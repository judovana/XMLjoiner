/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmljoiner;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import xmljoiner.impl.Joiner;
import xmljoiner.utils.Commons;

/**
 *
 * @author jvanek
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static final Map<String, Entry<String, String>> commandLineArgs = new HashMap();

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("-config - config file with preset params. default is default.conf, commandline params owerwrite in-file ones. If not set, then hardcoded are used");
            System.out.println("-headerXpath - xpath expression defining header elements. Default is nothing");
            System.out.println("-contentXpath - xpath expression defining body of chunk. Default is /*");
            System.out.println("-footerXpath - xpath expression defining footer of chunk. Default is nothing");
            System.out.println("-o - optional. output file for joined xml. When not set, output is std-out, when set without param, then file name is generated from inputfiles. If directory, outpufile will be guessed to this directory");
            System.out.println("-L - optional. loging level. 0-(defoult) all messages go to error Stream. 1 - messages go to stdout, 2- no messages");
            System.out.println("-delimiter1 - optional. will remove delimiter1(0-9)*delimiter2 from filename. default is _part_");
            System.out.println("-delimiter2 - optional.  will remove delimiter1(0-9)*delimiter2 from filename. default is none");
            System.out.println("-checkHeaders - optional.  will check headers and footers if they are coonsists over all files");
            System.out.println("-sortA - optional.  will sort input files alpahbetivcaly");
            System.out.println("-sortN - optional.  will try to sort  input files numericly");
            System.out.println("-i - optional.  regular expression for finding of input files. Reg.ex is following java conventions");
            System.out.println("http://download.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html");
            System.out.println("-ignoreUri -this is mechanism should help to use namespaces in XPaths. -ignoreuri or with arg ALL (-ignoreUri=ALL) will cause program to explicitly map all nmespaces in documetn. However - default namespaces are NOT mapped by this way.");
            System.out.println("            When you need to declare more namespaces (eg to map default namespaces) you must use possibility to write down as arg spcaes splitted text whic is describing prefixes and URIs. eg -ignoreUri=ss http://uri1/x pp fftp://uri1.xy .... (dont forget to protect spaces efor bash and to use same mappings in your xpaths)");
            System.out.println("             ALL cnan be used with ^^ eg.: \"-igonreURI=ALL prfix1 uri1 prefix2 uri2....\"");
            System.out.println("            please note, that the only possible way to proceed namespaces (and default ones) in xpath are prefixes in xpath. thing to work with namespaces is to write them to xpath PREFIX1:ELEMENT2/PREFIX3:ELEMENT4.... Or URI1:ELEMENT2/URI3:ELEMENT4 or mixture of uris/prefixes ");
            System.out.println("            namespaces is declared as prefix:uri in xml files in attribute xmlns) Default uri is without prefix and ust be mapped explicitly");
            System.out.println("any other argument is cosidered as input file, notexisting are skipped, user warned");
            System.out.println("-version - prints out program version");
            System.out.println("");
            System.out.println("Program by Vaněk Jiří, judovana@email.cz");
            System.exit(0);
        }

        Joiner splitter = new Joiner();

        for (String arg : args) {
            if (arg.replaceAll("-", "").equalsIgnoreCase("version")) {
                System.out.println("XmlJoiner version " + Joiner.VERSION);
                System.exit(0);
            }
            Entry<String, Entry<String, String>> e = Commons.paarseCommandLineArgWithOrig(arg);
            commandLineArgs.put(e.getKey(), e.getValue());
        }

        if (commandLineArgs.containsKey("l")) {
            if (commandLineArgs.get("l") == null) {
                splitter.setLogging(new Integer("0"));
            } else {
                splitter.setLogging(new Integer(commandLineArgs.get("l").getValue()));
            }
        }

        if (commandLineArgs.containsKey("config")) {
            if (commandLineArgs.get("config").getValue() == null) {
                //extracts full path to about.jnlp
                ClassLoader cl = Main.class.getClassLoader();
                if (cl == null) {
                    cl = ClassLoader.getSystemClassLoader();
                }
                String s = cl.getResource("xmljoiner/Main.class").toString();
                if (s.contains("build/classes")) {
                    s = s.substring(s.indexOf(":") + 1);
                    s = s.substring(0, s.indexOf("build/classes") - 1);
                    s = s + "/XMLjoiner.jar";
                } else {
                    s = s.substring(0, s.indexOf("!"));
                    s = s.substring(s.indexOf(":") + 1);
                    s = s.substring(s.indexOf(":") + 1);
                }
                s = "file://" + s.replace("XMLjoiner.jar", "default.conf");

                splitter.loadConfig(new URL(s));
            } else {
                splitter.loadConfig(new File(commandLineArgs.get("config").getValue()));
            }
        }
        Commons.proceedArgs(splitter, commandLineArgs);

        splitter.checkIntegrity();
        splitter.proceed();
    }
}
