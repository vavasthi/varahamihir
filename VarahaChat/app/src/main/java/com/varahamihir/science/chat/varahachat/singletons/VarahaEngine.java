package com.varahamihir.science.chat.varahachat.singletons;

import android.content.Context;

import com.myscript.iink.Configuration;
import com.myscript.iink.Engine;
import com.myscript.iink.ParameterSet;
import com.varahamihir.science.chat.varahachat.utils.FileUtils;

public class VarahaEngine {

    private static final byte[] certificateBytes = new byte[]{
            91,   113,  -20,  81,   68,   85,   27,   -93,
            78,   -85,  -107, 57,   123,  -21,  20,   126,
            123,  61,   14,   -65,  -34,  -38,  76,   -54,
            -118, -98,  -51,  -21,  -86,  -39,  88,   -107,
            39,   39,   -42,  4,    121,  -19,  54,   -120,
            -124, 67,   115,  31,   -47,  13,   64,   98,
            -84,  -2,   -109, -107, -51,  -19,  10,   39,
            110,  -60,  -107, -18,  -21,  101,  -54,  68,
            69,   101,  -40,  70,   117,  -93,  66,   119,
            10,   36,   -78,  -70,  4,    -5,   -48,  109,
            116,  11,   110,  22,   -95,  -54,  -101, -122,
            -126, 28,   5,    48,   50,   102,  -15,  -58,
            46,   10,   -126, 93,   34,   105,  -52,  -89,
            -8,   -18,  -73,  109,  108,  -83,  106,  -31,
            -113, 0,    14,   9,    -111, -17,  61,   -35,
            30,   59,   6,    92,   64,   -78,  -30,  27,
            -51,  -13,  -66,  62,   -126, -57,  59,   102,
            -25,  55,   -16,  -111, 123,  -121, 28,   112,
            -66,  -13,  28,   -117, -26,  115,  -27,  -35,
            117,  -127, 82,   -111, -70,  -57,  -63,  -29,
            115,  76,   -8,   37,   16,   70,   -18,  101,
            -75,  -55,  72,   -82,  1,    95,   -44,  75,
            -126, 53,   -102, -118, 114,  -105, -57,  -83,
            -79,  -14,  106,  88,   -121, 81,   -40,  15,
            -45,  87,   -21,  84,   -78,  -106, -20,  -96,
            21,   -126, 43,   -120, 120,  -8,   -83,  92,
            -19,  -58,  22,   -18,  -64,  -55,  -21,  -51,
            -37,  117,  93,   -48,  72,   -75,  117,  5,
            65,   -43,  -107, -61,  114,  -122, -95,  -66,
            24,   -23,  68,   -19,  64,   87,   -85,  117,
            -116, 73,   -110, 40,   -1,   -34,  32,   48,
            -82,  77,   83,   -54,  101,  66,   28,   -21,
            34,   -89,  109,  30,   -82,  72,   28,   65,
            124,  83,   -42,  86,   -102, -76,  55,   -126,
            70,   -91,  111,  30,   68,   18,   -117, 66,
            -80,  40,   69,   85,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -101, -76,  55,   -126,
            39,   70,   -78,  28,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  68,   18,   -117, 66,
            -64,  21,   101,  87,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -101, -76,  55,   -126,
            -9,   -39,  90,   27,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  68,   18,   -117, 66,
            -6,   -70,  -103, 84,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -101, -76,  55,   -126,
            -107, -28,  -47,  27,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  68,   18,   -117, 66,
            96,   -120, 70,   82,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -101, -76,  55,   -126,
            18,   41,   -26,  31,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  71,   18,   -117, 66,
            125,  32,   18,   83,   70,   -69,  55,   -126,
            33,   -91,  111,  30,   -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -104, -76,  55,   -126,
            70,   -97,  107,  28,   -101, 29,   -117, 66,
            120,  -34,  -15,  86,   101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  71,   18,   -117, 66,
            -100, -34,  5,    85,   120,  -69,  55,   -126,
            38,   -91,  111,  30,   -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -104, -76,  55,   -126,
            41,   61,   -116, 28,   -94,  29,   -117, 66,
            120,  -34,  -15,  86,   101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  71,   18,   -117, 66,
            39,   -119, -112, 87,   113,  -69,  55,   -126,
            42,   -91,  111,  30,   -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -104, -76,  55,   -126,
            -13,  -109, -39,  29,   -79,  29,   -117, 66,
            118,  -34,  -15,  86,   101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  71,   18,   -117, 66,
            4,    108,  39,   82,   100,  -69,  55,   -126,
            43,   -91,  111,  30,   -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -104, -76,  55,   -126,
            -67,  -43,  104,  31,   67,   2,    -117, 66,
            123,  -34,  -15,  86,   101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  71,   18,   -117, 66,
            101,  -99,  -65,  87,   113,  -69,  55,   -126,
            32,   -91,  111,  30,   -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -104, -76,  55,   -126,
            -101, -47,  19,   27,   72,   2,    -117, 66,
            116,  -34,  -15,  86,   101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  65,   18,   -117, 66,
            33,   -119, 4,    82,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -98,  -76,  55,   -126,
            -55,  8,    -96,  29,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  65,   18,   -117, 66,
            -87,  -15,  -80,  85,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -98,  -76,  55,   -126,
            111,  78,   114,  29,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            102,  -99,  41,   82,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            74,   0,    28,   31,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            113,  -38,  5,    85,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            92,   -128, -55,  28,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            94,   -68,  -110, 82,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -88,  38,   -41,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            87,   -57,  23,   86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            117,  118,  -104, 30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            58,   -44,  -40,  87,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -111, 51,   66,   31,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            20,   -44,  120,  87,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            3,    66,   -99,  31,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -2,   -49,  9,    87,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            107,  20,   95,   28,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -12,  119,  -61,  84,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            124,  126,  51,   28,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -115, -76,  -82,  84,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            26,   59,   -61,  28,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            78,   8,    45,   84,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            7,    -72,  110,  29,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            48,   -47,  -111, 85,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -31,  21,   10,   29,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -43,  54,   -105, 85,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -117, -1,   2,    29,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -113, -110, 116,  85,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            36,   10,   -59,  29,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            105,  52,   92,   85,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            51,   72,   -42,  29,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -114, -74,  48,   85,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            31,   110,  -119, 29,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            51,   88,   25,   85,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -101, 75,   -112, 29,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -121, -28,  -14,  82,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -81,  72,   102,  26,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            6,    -89,  -90,  82,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            29,   -88,  54,   26,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -52,  76,   -114, 82,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -116, 16,   -5,   26,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -27,  -99,  102,  82,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -58,  34,   -10,  26,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -3,   29,   89,   82,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            75,   9,    -35,  26,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            53,   125,  57,   82,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -6,   -19,  -75,  26,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            79,   35,   46,   82,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            60,   -101, -101, 26,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            55,   -39,  -13,  83,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            82,   -94,  100,  27,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -53,  29,   -4,   83,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            24,   32,   90,   27,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            13,   57,   -87,  83,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -123, 67,   53,   27,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -25,  67,   -103, 83,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -22,  -15,  2,    27,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            41,   127,  -126, 83,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -72,  -47,  27,   27,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -113, -63,  124,  83,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -10,  -29,  -4,   27,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -1,   9,    79,   83,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            83,   2,    -70,  27,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            13,   -120, 9,    84,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            62,   26,   81,   29,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -3,   79,   64,   86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            80,   54,   93,   27,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -64,  -107, -83,  85,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -110, 127,  7,    28,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -115, 62,   85,   84,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            32,   109,  101,  26,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -103, -105, -126, 87,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -30,  88,   -76,  31,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            43,   -61,  41,   84,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            17,   42,   5,    31,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            51,   49,   31,   82,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -56,  -20,  -94,  26,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -23,  -22,  -116, 87,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -103, -76,  55,   -126,
            -91,  107,  3,    31,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            56,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  70,   18,   -117, 66,
            -25,  18,   -96,  83,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            103,  -34,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -97,  -76,  55,   -126,
            35,   -91,  111,  30,   80,   2,    -117, 66,
            78,   -33,  -15,  86,   101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  67,   18,   -117, 66,
            -125, 33,   14,   -87,  -35,  -91,  55,   -126,
            19,   -91,  111,  30,   -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  101,  75,   -56,  125,
            -36,  90,   -112, -31,  -70,  -19,  116,  -67,
            -125, 33,   14,   -87,  -88,  -16,  122,  -29,
            87,   -51,  60,   118,  36,   98,   -18,  22,
            25,   -90,  -123, 31,   -12,  -33,  100,  -25,
            66,   -41,  12,   118,  21,   96,   -18,  38,
            21,   -67,  -123, 63,   -11,  -38,  115,  -19,
            64,   -48,  2,    123,  43,   102,  -52,  39,
            15,   -86,  -124, 36,   -1,   -11,  89,   -29,
            79,   -36,  21,   123,  55,   79,   -78,  0,
            100,  123,  -64,  -31,  -102, -111, 71,   -125,
            -77,  -107, -19,  31,   103,  34,   -122, 68,
            117,  -12,  119,  30,   28,   67,   58,   -125,
            34,   -92,  106,  30,   70,   -112, -118, 77,
            124,  -18,  115,  87,   -112, -74,  -75,  -125,
            34,   -91,  -63,  118,  -27,  70,   -80,  -19,
            16,   111,  100,  5,    -10,  66,   93,   55,
            -6,   -8,   -33,  -114, -125, 84,   103,  1,
            29,   126,  -18,  -128, 32,   88,   -9,   -48,
            -111, 6,    7,    -104, -73,  33,   -91,  -50,
            -88,  25,   -127, -80,  -1,   -60,  -48,  -2,
            -14,  26,   -72,  28,   32,   84,   -128, 7,
            -19,  -73,  -81,  69,   -32,  -16,  -38,  44,
            -21,  -7,   -102, 24,   -19,  -54,  116,  -118,
            69,   -103, 94,   33,   59,   -53,  106,  64,
            -101, 41,   90,   -46,  -20,  74,   -36,  -53,
            100,  -46,  72,   121,  -106, -119, 35,   -97,
            60,   -120, 29,   -12,  -110, -95,  -28,  -40,
            35,   -56,  -102, -120, 65,   96,   -107, -12,
            22,   -48,  -44,  95,   85,   30,   -85,  40,
            -112, -127, -82,  44,   74,   118,  97,   100,
            -43,  -22,  -31,  -24,  -86,  -104, -8,   -45,
            37,   82,   101,  83,   -19,  54,   27,   116,
            -60,  105,  68,   22,   81,   -61,  105,  -85,
            41,   113,  -71,  99,   27,   -69,  43,   111,
            107,  94,   67,   106,  -77,  112,  -44,  -39,
            102,  125,  59,   -15,  63,   -16,  77,   80,
            103,  -65,  -16,  -124, -9,   89,   -2,   -1,
            -105, -6,   25,   -120, 6,    -56,  89,   66,
            -121, -64,  108,  -30,  -92,  -27,  -21,  -66,
            119,  -5,   17,   -66,  -123, 113,  105,  -48,
            34,   -100, -89,  -106, 12,   -66,  81,   111,
            76,   -75,  65,   118,  26,   47,   -15,  65,
            -46,  -33,  -7,   49,   29,   -11,  38,   21,
            -64,  -79,  12,   47,   40,   48,   -21,  -122,
            -125, -24,  58,   -121, -49,  77,   43,   -21,
            -18,  -40,  -46,  -95,  -42,  28,   -38,  -119,
            102,  68,   109,  29,   68,   18,   -118, 42,
            8,    -86,  -127, 37,   -96,  -101, 24,   -29,
            87,   -50,  65,   115,  60,   97,   -24,  48,
            21,   -82,  -123, 120,  -7,   -37,  90,   -83,
            71,   -64,  25,   119,  38,   119,  -76,  35,
            12,   -82,  -72,  18,   -89,  -111, 68,   -92,
            71,   -60,  27,   127,  120,  55,   -8
    };

    private Engine engine;
    public static VarahaEngine INSTANCE = new VarahaEngine();
    private VarahaEngine() {
        engine = Engine.create(certificateBytes);
    }
    public void initializeConfiguration(Context context) {

        Configuration configuration = engine.getConfiguration();
        String confDir = "zip://" + context.getPackageCodePath() + "!/assets/conf";
        configuration.setStringArray("configuration-manager.search-path", new String[]{confDir});
        configuration.setString("content-package.temp-folder", FileUtils.getPrivateTmpStorageDir(context).getAbsolutePath());
    }
    public Engine getEngine() {
        return engine;
    }
}
