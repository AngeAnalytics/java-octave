/*
 * Copyright 2008 Ange Optimization ApS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * @author Kim Hansen
 */
package dk.ange.octave.io;

import java.io.BufferedReader;

import dk.ange.octave.OctaveIO;
import dk.ange.octave.OctaveReadHelper;
import dk.ange.octave.exception.OctaveParseException;
import dk.ange.octave.type.OctaveCell;
import dk.ange.octave.type.OctaveType;

/**
 * The reader of cell
 */
public final class CellReader implements OctaveDataReader {

    public String octaveType() {
        return "cell";
    }

    public OctaveCell read(final BufferedReader reader) {
        String line;
        String token;
        line = OctaveReadHelper.readerReadLine(reader);
        token = "# type: cell";
        if (!line.equals(token)) {
            throw new OctaveParseException("Expected <" + token + ">, but got <" + line + ">");
        }

        line = OctaveReadHelper.readerReadLine(reader);
        token = "# rows: ";
        if (!line.startsWith(token)) {
            throw new OctaveParseException("Expected <" + token + ">, but got <" + line + ">");
        }
        final int nrows = Integer.parseInt(line.substring(token.length()));

        line = OctaveReadHelper.readerReadLine(reader);
        token = "# columns: ";
        if (!line.startsWith(token)) {
            throw new OctaveParseException("Expected <" + token + ">, but got <" + line + ">");
        }
        final int ncols = Integer.parseInt(line.substring(token.length()));

        final OctaveCell octaveCell = new OctaveCell(nrows, ncols);

        for (int col = 1; col <= ncols; col++) {
            for (int row = 1; row <= nrows; row++) {
                line = OctaveReadHelper.readerReadLine(reader);
                token = "# name: <cell-element>";
                if (!line.equals(token)) {
                    throw new OctaveParseException("Expected <" + token + ">, but got <" + line + ">");
                }
                final OctaveType octaveType = OctaveIO.read(reader, false);
                octaveCell.set(row, col, octaveType);
            }
            line = OctaveReadHelper.readerReadLine(reader);
            token = "";
            if (!line.equals(token)) {
                throw new OctaveParseException("Expected <" + token + ">, but got <" + line + ">");
            }
        }

        return octaveCell;
    }

}
