/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.harmony.jretools.pack200;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.jar.JarInputStream;

import org.apache.harmony.pack200.Archive;

public class Main {

    public static void main(String args[]) throws Exception {

        String inputFileName = null;
        String outputFileName = null;
        boolean verbose = false;
        boolean quiet = false;
        String logFileName = null;
        boolean gzip = true;
        int segmentLimit = -2;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--help") || args[i].equals("-help")
                    || args[i].equals("-h") || args[i].equals("-?")) {
                printHelp();
                return;
            } else if(args[i].equals("-g") || args[i].equals("--no-gzip")) {
                gzip = false;
            } else if(args[i].equals("--gzip")) {
                gzip = true;
            }  else if(args[i].equals("-v") || args[i].equals("--verbose")) {
                verbose = true;
                quiet = false;
            } else if(args[i].equals("-q") || args[i].equals("--quiet")) {
                quiet = true;
                verbose = false;
            } else if(args[i].startsWith("-l")) {
                logFileName = args[i].substring(2);
            } else if(args[i].equals("-V") || args[i].equals("--version")) {
                printVersion();
                return;
            } else if(args[i].equals("-S") || args[i].equals("--segment-limit")) {
                segmentLimit = Integer.parseInt(args[++i]);
            } else {
                outputFileName = args[i];
                if(args.length > i + 1) {
                    inputFileName = args[i+1];
                }
                break;
            }
        }
        if(inputFileName == null || outputFileName == null) {
            printUsage();
            return;
        }
        JarInputStream inputStream = new JarInputStream(new FileInputStream(inputFileName));
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFileName));
        Archive archive = new Archive(inputStream, outputStream, gzip);
        if(segmentLimit > -2) {
            archive.setSegmentLimit(segmentLimit);
        }
        archive.pack();
    }

    private static void printUsage() {
        System.out.println("Usage:  pack200 [-opt... | --option=value]... x.pack[.gz] y.jar");
        System.out.println("(For more information, run pack200 --help)");
    }

    private static void printHelp() {
        // TODO: Support all the options
        System.out.println("Usage:  pack200 [-opt... | --option=value]... x.pack[.gz] y.jar");
        System.out.println();
        System.out.println("Packing Options");
        System.out.println("  -g, --no-gzip                   output a plain *.pack file with no zipping");
        System.out.println("  --gzip                          (default) post-process the pack output with gzip");
//        System.out.println("  -G, --strip-debug               remove debugging attributes while packing");
//        System.out.println("  -O, --no-keep-file-order        do not transmit file ordering information");
//        System.out.println("  --keep-file-order               (default) preserve input file ordering");
        System.out.println("  -S{N}, --segment-limit={N}      output segment limit (default N=1Mb)");
//        System.out.println("  -E{N}, --effort={N}             packing effort (default N=5)");
//        System.out.println("  -H{h}, --deflate-hint={h}       transmit deflate hint: true, false, or keep (default)");
//        System.out.println("  -m{V}, --modification-time={V}  transmit modtimes: latest or keep (default)");
//        System.out.println("  -P{F}, --pass-file={F}          transmit the given input element(s) uncompressed");
//        System.out.println("  -U{a}, --unknown-attribute={a}  unknown attribute action: error, strip, or pass (default)");
//        System.out.println("  -C{N}={L}, --class-attribute={N}={L}  (user-defined attribute)");
//        System.out.println("  -F{N}={L}, --field-attribute={N}={L}  (user-defined attribute)");
//        System.out.println("  -M{N}={L}, --method-attribute={N}={L} (user-defined attribute)");
//        System.out.println("  -D{N}={L}, --code-attribute={N}={L}   (user-defined attribute)");
//        System.out.println("  -f{F}, --config-file={F}        read file F for Pack200.Packer properties");
//        System.out.println("  -v, --verbose                   increase program verbosity");
//        System.out.println("  -q, --quiet                     set verbosity to lowest level");
//        System.out.println("  -l{F}, --log-file={F}           output to the given log file, or '-' for System.out");
        System.out.println("  -?, -h, --help                  print this message");
        System.out.println("  -V, --version                   print program version");
//        System.out.println("  -J{X}                           pass option X to underlying Java VM");
//        System.out.println("");
//        System.out.println("Notes:");
//        System.out.println("  The -P, -C, -F, -M, and -D options accumulate.");
//        System.out.println("  Example attribute definition:  -C SourceFile=RUH .");
//        System.out.println("  Config. file properties are defined by the Pack200 API.");
//        System.out.println("  For meaning of -S, -E, -H-, -m, -U values, see Pack200 API.");
//        System.out.println("  Layout definitions (like RUH) are defined by JSR 200.");
//        System.out.println("");
//        System.out.println("Repacking mode updates the JAR file with a pack/unpack cycle:");
//        System.out.println("    pack200 [-r|--repack] [-opt | --option=value]... [repackedy.jar] y.jar");
    }

    private static void printVersion() {
        System.out.println("Apache Harmony pack200 version 0.0");  // TODO - version number
    }

}