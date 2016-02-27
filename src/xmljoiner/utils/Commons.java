/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmljoiner.utils;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import xmljoiner.impl.Joiner;

/**
 *
 * @author jvanek
 */
public class Commons {

    public static Entry<String, Entry<String, String>> paarseCommandLineArgWithOrig(String s) {
        Entry<String, String> qq = paarseCommandLineArg(s);
        return new EntryImpl2(qq.getKey(), new EntryImpl(s, qq.getValue()));
    }

    public static Entry<String, String> paarseCommandLineArg(String s) {

        s = s.replaceAll("^-*", "");
        String[] ss = s.split(" *= *");
        String key = ss[0].toLowerCase();
        String value = null;
        if (ss.length > 1) {
            value = s.substring(key.length() + 1);
            value = value.replaceAll("^ *= *", "");
        }
        return new EntryImpl(key, value);
    }

    public static void log(String string, int level) {
        if (level == 0) {
            System.err.println(string);
        } else if (level == 1) {
            System.out.println(string);
        }
    }

    public static String spacing(int chNumber) {
        String r = String.valueOf(chNumber);
        while (r.length() < 5) {
            r = "0" + r;
        }

        return r;
    }

    private static class EntryImpl implements Entry<String, String> {

        private final String key;
        private String value;

        private EntryImpl(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String setValue(String v) {
            String q = value;
            value = v;
            return q;
        }
    }

    private static class EntryImpl2 implements Entry<String, Entry<String, String>> {

        private final String key;
        private Entry<String, String> value;

        private EntryImpl2(String key, Entry<String, String> value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public Entry<String, String> getValue() {
            return value;
        }

        public Entry<String, String> setValue(Entry<String, String> v) {
            Entry<String, String> q = value;
            value = v;
            return q;
        }
    }

    public static void proceedArgs(Joiner splitter, Map<String, Entry<String, String>> params) {
        Set<Entry<String, Entry<String, String>>> cmds = params.entrySet();
        for (Map.Entry<String, Entry<String, String>> entry : cmds) {
            proceedArg(splitter, entry);
        }
    }

    public static void proceedArg(Joiner splitter, Entry<String, Entry<String, String>> entry) {

        String key = entry.getKey();
        String value = entry.getValue().getValue();
        if (key.equals("ignoreuri")) {
            if (value == null) {
                Commons.log("ignoreUri  without arg. Used ALL", splitter.getLogging());
                value = "ALL";
            }
            splitter.setIgnoreUri(value);

        } else if (key.equals("checkheaders")) {
            if (value != null) {
                Commons.log("check headers with arg. ignored", splitter.getLogging());

            }
            splitter.setCheckHeaders(true);
        } else if (key.equals("o")) {
            if (value == null) {
                Commons.log("output file without arg. Name will be guessed", splitter.getLogging());
                value = "";
            }
            splitter.setOutputFile(new File(value));
        } else if (key.equals("i")) {
            if (value == null) {
                Commons.log("input file  without arg. ignored", splitter.getLogging());
            } else {
                String regex = new File(value).getName();
                File dir = new File(value).getParentFile();
                if (dir == null) {
                    dir = new File(System.getProperty("user.dir")).getAbsoluteFile();
                }
                Commons.log("processing " + regex + " in " + dir.getAbsolutePath(), splitter.getLogging());
                File[] f = dir.listFiles();
                Commons.log(f.length + " candidates", splitter.getLogging());
                for (File file : f) {
                    if (file.getName().matches(regex)) {
                        splitter.addInput(file);
                    }
                }

            }
        } else if (key.equals("headerxpath")) {
            if (value == null) {
                Commons.log("headerXpath without arg. ignored", splitter.getLogging());
            } else {
                splitter.setHeaderXpath(value);
            }
        } else if (key.equals("contentxpath")) {
            if (value == null) {
                Commons.log("contentXpath without arg. ignored", splitter.getLogging());
            } else {
                splitter.setContentXpath(value);
            }
        } else if (key.equals("footerxpath")) {
            if (value == null) {
                Commons.log("footerXpath without arg. ignored", splitter.getLogging());
            } else {
                splitter.setFooterXpath(value);
            }
        } else if (key.equals("delimiter1")) {
            if (value == null) {
                Commons.log("delimiter1 without arg. ignored", splitter.getLogging());
            } else {
                splitter.setDelimiter1(value);
            }
        } else if (key.equals("delimiter2")) {
            if (value == null) {
                Commons.log("delimiter2 without arg. ignored", splitter.getLogging());
            } else {
                splitter.setDelimiter2(value);
            }
        } else if (key.equals("sorta")) {
            if (value != null) {
                Commons.log("sort with arg. ignored", splitter.getLogging());
            }
            splitter.setSort(1);
        } else if (key.equals("sortn")) {
            if (value != null) {
                Commons.log("sort with arg. ignored", splitter.getLogging());
            }
            splitter.setSort(2);
        } else if (key.equals("config") || key.equals("l")) {/*procesed before everything*/

        } else {
            String candidate = entry.getValue().getKey();
            Commons.log("checking " + candidate, splitter.getLogging());
            File f = new File(candidate);
            splitter.addInput(f);
        }
    }

}
