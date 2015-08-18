/*
 * Copyright (C) 2015 Jörg Prante
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * The interactive user interfaces in modified source and object code
 * versions of this program must display Appropriate Legal Notices,
 * as required under Section 5 of the GNU Affero General Public License.
 *
 */
package org.xbib.elasticsearch.index.analysis.symbolname;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PackedTokenAttributeImpl;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymbolnameTokenFilter extends TokenFilter {

    private final LinkedList<PackedTokenAttributeImpl> tokens;

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

    private final PositionIncrementAttribute posIncAtt = addAttribute(PositionIncrementAttribute.class);

    private State current;

    protected SymbolnameTokenFilter(TokenStream input) {
        super(input);
        this.tokens = new LinkedList<PackedTokenAttributeImpl>();
    }

    @Override
    public final boolean incrementToken() throws IOException {
        if (!tokens.isEmpty()) {
            assert current != null;
            PackedTokenAttributeImpl token = tokens.removeFirst();
            restoreState(current);
            termAtt.setEmpty().append(token);
            offsetAtt.setOffset(token.startOffset(), token.endOffset());
            posIncAtt.setPositionIncrement(0);
            return true;
        }
        if (input.incrementToken()) {
            process();
            if (!tokens.isEmpty()) {
                current = captureState();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        tokens.clear();
        current = null;
    }

    protected void process() throws CharacterCodingException {
        String term = new String(termAtt.buffer(), 0, termAtt.length());
        Collection<CharSequence> variants = new LinkedList<>();
        variants.addAll(process(term));
        for (CharSequence ch : variants) {
            if (ch != null) {
                PackedTokenAttributeImpl token = new PackedTokenAttributeImpl();
                token.append(ch);
                tokens.add(token);
            }
        }
    }

    protected Collection<CharSequence> process(String term) {
        Collection<CharSequence> variants = new LinkedList<>();
        StringBuffer sb = new StringBuffer();
        Matcher m = pattern.matcher(term);
        while (m.find()) {
            String symbol = m.group();
            Character ch = symbol.charAt(0);
            String symbolname = " __" + Character.getName(ch.charValue())
                    .replaceAll("\\s","").replaceAll("\\-","") + "__";
            m.appendReplacement(sb, symbolname);
        }
        m.appendTail(sb);
        String variant = sb.toString().trim();
        if (!variant.equals(term)) {
            variants.add(variant);
            if (variant.indexOf(' ') > 0) {
                Collections.addAll(variants, variant.split("\\s"));
            }
        }
        return variants;
    }

    private final static Pattern pattern = Pattern.compile("\\P{L}", Pattern.UNICODE_CHARACTER_CLASS);

}