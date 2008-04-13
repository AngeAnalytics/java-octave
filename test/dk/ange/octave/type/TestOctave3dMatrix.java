/*
 * Copyright 2007, 2008 Ange Optimization ApS
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
package dk.ange.octave.type;

import junit.framework.Assert;
import junit.framework.TestCase;
import dk.ange.octave.Octave;
import dk.ange.octave.io.OctaveIO;

/**
 * @author Kim Hansen
 */
public class TestOctave3dMatrix extends TestCase {

    /**
     * @throws Exception
     */
    public void testConstructor() throws Exception {
        final OctaveNdMatrix matrix = new OctaveNdMatrix(0, 0, 0);

        Assert.assertEquals("# name: matrix3d\n# type: matrix\n# ndims: 3\n 0 0 0\n\n", OctaveIO.toText(matrix,
                "matrix3d"));
    }

    /**
     * @throws Exception
     */
    public void testConstructorIntIntInt() throws Exception {
        final OctaveNdMatrix matrix = new OctaveNdMatrix(3, 4, 2);
        Assert.assertEquals("" //
                + "# name: matrix3d\n" //
                + "# type: matrix\n" //
                + "# ndims: 3\n" //
                + " 3 4 2\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + "\n", OctaveIO.toText(matrix, "matrix3d"));
        matrix.set(42.0, 1, 3, 2);
        Assert.assertEquals("" //
                + "# name: matrix3d\n" //
                + "# type: matrix\n" //
                + "# ndims: 3\n" //
                + " 3 4 2\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + " 42.0\n 0.0\n 0.0\n" //
                + " 0.0\n 0.0\n 0.0\n" //
                + "\n", OctaveIO.toText(matrix, "matrix3d"));
    }

    /**
     * @throws Exception
     */
    public void testOctave() throws Exception {
        final OctaveNdMatrix matrix3d = new OctaveNdMatrix(3, 4, 2);

        matrix3d.set(42.0, 1, 3, 2);
        matrix3d.set(-1.0, 3, 1, 1);
        final Octave octave = new Octave();
        octave.set("matrix3d", matrix3d);
        octave.execute("x1 = matrix3d(:,:,1);");
        octave.execute("x2 = matrix3d(:,:,2);");
        octave.execute("x3 = matrix3d(:,3,:);");
        octave.execute("x4 = matrix3d(3,:,:);");
        final OctaveNdMatrix x1 = octave.get("x1");
        final OctaveNdMatrix x2 = octave.get("x2");
        final OctaveNdMatrix x3 = octave.get("x3");
        final OctaveNdMatrix x4 = octave.get("x4");
        assertEquals(0.0, x1.get(1, 3));
        assertEquals(-1.0, x1.get(3, 1));
        assertEquals(42.0, x2.get(1, 3));
        assertEquals(0.0, x2.get(3, 1));
        assertEquals(42.0, x3.get(1, 1, 2));
        assertEquals(-1.0, x4.get(1, 1, 1));
        octave.close();
    }
}
