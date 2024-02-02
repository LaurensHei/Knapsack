package nl.group37.algorithm;

public class ParcelList {

        public static int[][][][] tPentominoes = {
            {   // T Pentomino
                    {{1, 1, 1}},
                    {{0, 1, 0}},
                    {{0, 1, 0}}
            },

            {   // T Pentomino mutation 1
                    {{0, 0, 1}},
                    {{1, 1, 1}},
                    {{0, 0, 1}}
            },

            {   // T Pentomino mutation 2
                    {{0, 1, 0}},
                    {{0, 1, 0}},
                    {{1, 1, 1}}
            },

            {   // T Pentomino mutation 3
                    {{1, 0, 0}},
                    {{1, 1, 1}},
                    {{1, 0, 0}}
            },

            {   // T Pentomino mutation 4
                    {{1}, {1}, {1}},
                    {{0}, {1}, {0}},
                    {{0}, {1}, {0}}
            },

            {   // T Pentomino mutation 5
                    {{0}, {0}, {1}},
                    {{1}, {1}, {1}},
                    {{0}, {0}, {1}}
            },

            {   // T Pentomino mutation 6
                    {{0}, {1}, {0}},
                    {{0}, {1}, {0}},
                    {{1}, {1}, {1}}
            },

            {   // T Pentomino mutation 7
                    {{1}, {0}, {0}},
                    {{1}, {1}, {1}},
                    {{1}, {0}, {0}}
            },

            {   // T Pentomino mutation 8
                    {{1, 1, 1}, {0, 1, 0}, {0, 1, 0}},
            },

            {   // T Pentomino mutation 9
                    {{1, 0, 0}, {1, 1, 1}, {1, 0, 0}},
            },

            {   // T Pentomino mutation 10
                    {{0, 1, 0}, {0, 1, 0}, {1, 1, 1}},
            },

            {   // T Pentomino mutation 11
                    {{0, 0, 1}, {1, 1, 1}, {0, 0, 1}},
            },
    };

    public static int[][][][] lPentominoes = {
            //parcel L representation
            {
                    {{1, 0, 0, 0}},
                    {{1, 1, 1, 1}}
            },
            //parcel L 1st mutation
            {
                    {{1, 1}},
                    {{1, 0}},
                    {{1, 0}},
                    {{1, 0}}
            },
            //parcel L 2nd mutation
            {
                    {{1, 1, 1, 1}},
                    {{0, 0, 0, 1}}
            },
            //parcel L 3rd mutation
            {
                    {{0, 1}},
                    {{0, 1}},
                    {{0, 1}},
                    {{1, 1}}
            },
            //parcel L 4th mutation
            {
                    {{0, 0, 0, 1}},
                    {{1, 1, 1, 1}}
            },
            //parcel L 5th mutation
            {
                    {{1, 0}},
                    {{1, 0}},
                    {{1, 0}},
                    {{1, 1}}
            },
            //parcel L 6th mutation
            {
                    {{1, 1, 1, 1}},
                    {{1, 0, 0, 0}}
            },
            //parcel L 7th mutation
            {
                    {{1, 1}},
                    {{0, 1}},
                    {{0, 1}},
                    {{0, 1}}
            },
            //parcel L 8th mutation
            {
                    {{1}, {1}, {1}, {1}},
                    {{1}, {0}, {0}, {0}}
            },
            //parcel L 9th mutation
            {
                    {{1}, {1}, {1}, {1}},
                    {{0}, {0}, {0}, {1}}
            },
            //parcel L 10th mutation
            {
                    {{0}, {0}, {0}, {1}},
                    {{1}, {1}, {1}, {1}}
            },
            //parcel L 11th mutation
            {
                    {{1}, {0}, {0}, {0}},
                    {{1}, {1}, {1}, {1}}
            },
            //parcel L 12th mutation
            {
                    {{1}, {1}},
                    {{1}, {0}},
                    {{1}, {0}},
                    {{1}, {0}},
            },
            //parcel L 13th mutation
            {
                    {{1}, {1}},
                    {{0}, {1}},
                    {{0}, {1}},
                    {{0}, {1}},
            },
            //parcel L 14th mutation
            {
                    {{1}, {0}},
                    {{1}, {0}},
                    {{1}, {0}},
                    {{1}, {1}},
            },
            //parcel L 15th mutation
            {
                    {{0}, {1}},
                    {{0}, {1}},
                    {{0}, {1}},
                    {{1}, {1}},
            },
            //parcel L 16th mutation
            {
                    {{1, 1}, {1, 0}, {1, 0}, {1, 0}}
            },
            //parcel L 17th mutation
            {
                    {{1, 0}, {1, 0}, {1, 0}, {1, 1}}
            },
            //parcel L 18th mutation
            {
                    {{1, 1}, {0, 1}, {0, 1}, {0, 1}}
            },
            //parcel L 19th mutation
            {
                    {{0, 1}, {0, 1}, {0, 1}, {1, 1}}
            },
            //parcel L 20th mutation
            {
                    {{1, 1, 1, 1}, {1, 0, 0, 0}}
            },
            //parcel L 21st mutation
            {
                    {{1, 1, 1, 1}, {0, 0, 0, 1}}
            },
            //parcel L 22nd mutation
            {
                    {{1, 0, 0, 0}, {1, 1, 1, 1}}
            },
            //parcel L 23rd mutation
            {
                    {{0, 0, 0, 1}, {1, 1, 1, 1}}
            },
    };

    public static int[][][][] pPentominoes = {
            // parcel p mutations
            {
                    //first mutation
                    {{1, 1}},
                    {{1, 1}},
                    {{1, 0}}
            },
            {
                    //second mutation
                    {{1, 1}},
                    {{1, 1}},
                    {{0, 1}}
            },
            {
                    //third mutation
                    {{1, 0}},
                    {{1, 1}},
                    {{1, 1}}
            },
            {
                    // forth mutation
                    {{0, 1}},
                    {{1, 1}},
                    {{1, 1}}
            },
            {
                    //fifth  mutation
                    {{1}, {1}, {1}},
                    {{1}, {1}, {0}}
            },
            {
                    //sixth mutation
                    {{1}, {1}, {1}},
                    {{0}, {1}, {1}}
            },
            {
                    //seventh mutation
                    {{0}, {1}, {1}},
                    {{1}, {1}, {1}}
            },
            {
                    //eighth mutation
                    {{1}, {1}, {0}},
                    {{1}, {1}, {1}}
            },
            {
                    // ninth mutation
                    {{1, 1, 0}},
                    {{1, 1, 1}}
            },
            {
                    //tenth mutation
                    {{0, 1, 1}},
                    {{1, 1, 1}}
            },
            {
                    //eleventh mutation
                    {{1, 1, 1}},
                    {{0, 1, 1}}
            },
            {
                    // twelfth mutation
                    {{1, 1, 1}},
                    {{1, 1, 0}}
            },
            {
                    // thirteenth mutation
                    {{1}, {1}},
                    {{1}, {1}},
                    {{1}, {0}}
            },
            {
                    //fourteenth mutation
                    {{1}, {1}},
                    {{1}, {1}},
                    {{0}, {1}}
            },
            {
                    //fifteenth mutation
                    {{0}, {1}},
                    {{1}, {1}},
                    {{1}, {1}}
            },
            {
                    //sixteenth mutation
                    {{1}, {0}},
                    {{1}, {1}},
                    {{1}, {1}}
            },
            {
                    // seventeenth mutation
                    {{1, 1, 1}, {1, 1, 0}}
            },
            {
                    //eighteenth mutation
                    {{0, 1, 1}, {1, 1, 1}}
            },
            {
                    //nineteenth mutation
                    {{1, 1, 0}, {1, 1, 1}}
            },
            {
                    //twentieth mutation
                    {{1, 1, 1}, {0, 1, 1}}
            },
            {
                    //twenty-first mutation
                    {{1, 1}, {1, 1}, {1, 0}}
            },
            {
                    //twenty-second mutation
                    {{1, 1}, {1, 1}, {0, 1}}
            },
            {
                    //twenty-third mutation
                    {{1, 0}, {1, 1}, {1, 1}}
            },
            {
                    //twenty-fourth mutation
                    {{0, 1}, {1, 1}, {1, 1}}
            }
    };

}
