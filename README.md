# XMLjoiner
Tool to joining xml files based on xpath headers, body and tails
Note, the assembly jar dont have support of namesapces, you ne
! commandline arguments are overwriteing values in configs!
config files are expected to be shared between joiner and splitter

Original webpage - http://xmlsplit.wz.cz/  (and http://xmlcompare.wz.cz/)

# Example
```
pwd
 ~/NetBeansProjects/XMLsplitter/test/

ll test/alpha.xml 
 -rw-r--r-- 1 jvanek jvanek 16302753 Sep 16  2011 test/alpha.xml

java -jar dist/XMLsplitter.jar  -config=test/alpha.conf  -o=/tmp/ -i=test/alpha.xml 
 ...output1...

ls /tmp/alpha_part_000*
 /tmp/alpha_part_00001.xml  /tmp/alpha_part_00004.xml  /tmp/alpha_part_00007.xml  /tmp/alpha_part_00010.xml  /tmp/alpha_part_00013.xml  /tmp/alpha_part_00016.xml
 /tmp/alpha_part_00002.xml  /tmp/alpha_part_00005.xml  /tmp/alpha_part_00008.xml  /tmp/alpha_part_00011.xml  /tmp/alpha_part_00014.xml  /tmp/alpha_part_00017.xml
 /tmp/alpha_part_00003.xml  /tmp/alpha_part_00006.xml  /tmp/alpha_part_00009.xml  /tmp/alpha_part_00012.xml  /tmp/alpha_part_00015.xml

cd  ~/NetBeansProjects/XMLjoiner/alpha.xml

java -jar dist/XMLjoiner.jar  -config=test/alpha.conf  -o /tmp/alpha_part_000*
 ...output2...
ll alpha.xml 
 -rw-rw-r-- 1 jvanek jvanek 23121771 Feb 27 18:47 alpha.xml

echo "wait, md5sum and size differs!"
java -jar assembly/XMLcompare_assembly_mandatory.jar -orphanSeek -dual -silent -failFast ~/NetBeansProjects/XMLsplitter/test/alpha.xml  ~/NetBeansProjects/XMLjoiner/alpha.xml
 ...output3...
 XMLs are same

```
# ...output2...
```
# java -jar dist/XMLjoiner.jar  -config=test/alpha.conf  -o /tmp/alpha_part_000*
reading: /home/jvanek/NetBeansProjects/XMLjoiner/test/alpha.conf
header xpath setted: /ss:Workbook/o:DocumentProperties | /ss:Workbook/x:ExcelWorkbook | /ss:Workbook/ss:Styles
content xpath setted: /ss:Workbook/ss:Worksheet/ss:Table/ss:Column | /ss:Workbook/ss:Worksheet/ss:Table/ss:Row | /ss:Workbook/ss:Worksheet/x:WorksheetOptions
checking divSize=16
input file not exist. ignored: /home/jvanek/NetBeansProjects/XMLjoiner/divSize=16
output file without arg. Name will be guessed
output dir setted: /home/jvanek/NetBeansProjects/XMLjoiner
ignoreUri  without arg. Used ALL
sorting set to alhabetical
checking /tmp/alpha_part_00001.xml
input file added: /tmp/alpha_part_00001.xml
checking /tmp/alpha_part_00006.xml
input file added: /tmp/alpha_part_00006.xml
checking /tmp/alpha_part_00008.xml
input file added: /tmp/alpha_part_00008.xml
checking /tmp/alpha_part_00016.xml
input file added: /tmp/alpha_part_00016.xml
checking /tmp/alpha_part_00010.xml
input file added: /tmp/alpha_part_00010.xml
checking /tmp/alpha_part_00003.xml
input file added: /tmp/alpha_part_00003.xml
checking /tmp/alpha_part_00012.xml
input file added: /tmp/alpha_part_00012.xml
checking /tmp/alpha_part_00014.xml
input file added: /tmp/alpha_part_00014.xml
output file without arg. Name will be guessed
output dir setted: /home/jvanek/NetBeansProjects/XMLjoiner
checking /tmp/alpha_part_00002.xml
input file added: /tmp/alpha_part_00002.xml
checking /tmp/alpha_part_00005.xml
input file added: /tmp/alpha_part_00005.xml
checking /tmp/alpha_part_00009.xml
input file added: /tmp/alpha_part_00009.xml
checking /tmp/alpha_part_00007.xml
input file added: /tmp/alpha_part_00007.xml
checking /tmp/alpha_part_00004.xml
input file added: /tmp/alpha_part_00004.xml
checking /tmp/alpha_part_00011.xml
input file added: /tmp/alpha_part_00011.xml
checking /tmp/alpha_part_00017.xml
input file added: /tmp/alpha_part_00017.xml
checking /tmp/alpha_part_00015.xml
input file added: /tmp/alpha_part_00015.xml
checking /tmp/alpha_part_00013.xml
input file added: /tmp/alpha_part_00013.xml
logging: errorstream
delimiter1 set _part_
delimiter2 set 
header xpath: /ss:Workbook/o:DocumentProperties | /ss:Workbook/x:ExcelWorkbook | /ss:Workbook/ss:Styles
content xpath: /ss:Workbook/ss:Worksheet/ss:Table/ss:Column | /ss:Workbook/ss:Worksheet/ss:Table/ss:Row | /ss:Workbook/ss:Worksheet/x:WorksheetOptions
footer xpath not set
17 input files 
output file: /home/jvanek/NetBeansProjects/XMLjoiner/alpha.xml
input filesnow sorting alphabeticaly
/tmp/alpha_part_00001.xml
/tmp/alpha_part_00002.xml
/tmp/alpha_part_00003.xml
/tmp/alpha_part_00004.xml
/tmp/alpha_part_00005.xml
/tmp/alpha_part_00006.xml
/tmp/alpha_part_00007.xml
/tmp/alpha_part_00008.xml
/tmp/alpha_part_00009.xml
/tmp/alpha_part_00010.xml
/tmp/alpha_part_00011.xml
/tmp/alpha_part_00012.xml
/tmp/alpha_part_00013.xml
/tmp/alpha_part_00014.xml
/tmp/alpha_part_00015.xml
/tmp/alpha_part_00016.xml
/tmp/alpha_part_00017.xml
headers will NOT be checked
=======PROCESSING=======
writing to /home/jvanek/NetBeansProjects/XMLjoiner/alpha.xml
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1363 nodes!
content size is 1442377 bytes!)
adding header
adding chunk 0 size 1442377bytes in 1363 chunks from total 17 files
addded chunk 0 total output size 1442377bytes in 1363 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1354 nodes!
content size is 1443390 bytes!)
adding chunk 1 size 1443390bytes in 1354 chunks from total 17 files
addded chunk 1 total output size 2885767bytes in 2717 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1367 nodes!
content size is 1443313 bytes!)
adding chunk 2 size 1443313bytes in 1367 chunks from total 17 files
addded chunk 2 total output size 4329080bytes in 4084 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1389 nodes!
content size is 1443494 bytes!)
adding chunk 3 size 1443494bytes in 1389 chunks from total 17 files
addded chunk 3 total output size 5772574bytes in 5473 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1398 nodes!
content size is 1443204 bytes!)
adding chunk 4 size 1443204bytes in 1398 chunks from total 17 files
addded chunk 4 total output size 7215778bytes in 6871 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1375 nodes!
content size is 1443479 bytes!)
adding chunk 5 size 1443479bytes in 1375 chunks from total 17 files
addded chunk 5 total output size 8659257bytes in 8246 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1370 nodes!
content size is 1443857 bytes!)
adding chunk 6 size 1443857bytes in 1370 chunks from total 17 files
addded chunk 6 total output size 10103114bytes in 9616 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1389 nodes!
content size is 1443742 bytes!)
adding chunk 7 size 1443742bytes in 1389 chunks from total 17 files
addded chunk 7 total output size 11546856bytes in 11005 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1372 nodes!
content size is 1443891 bytes!)
adding chunk 8 size 1443891bytes in 1372 chunks from total 17 files
addded chunk 8 total output size 12990747bytes in 12377 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1377 nodes!
content size is 1444819 bytes!)
adding chunk 9 size 1444819bytes in 1377 chunks from total 17 files
addded chunk 9 total output size 14435566bytes in 13754 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1373 nodes!
content size is 1445196 bytes!)
adding chunk 10 size 1445196bytes in 1373 chunks from total 17 files
addded chunk 10 total output size 15880762bytes in 15127 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1370 nodes!
content size is 1445541 bytes!)
adding chunk 11 size 1445541bytes in 1370 chunks from total 17 files
addded chunk 11 total output size 17326303bytes in 16497 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1380 nodes!
content size is 1446425 bytes!)
adding chunk 12 size 1446425bytes in 1380 chunks from total 17 files
addded chunk 12 total output size 18772728bytes in 17877 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1383 nodes!
content size is 1446049 bytes!)
adding chunk 13 size 1446049bytes in 1383 chunks from total 17 files
addded chunk 13 total output size 20218777bytes in 19260 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1394 nodes!
content size is 1447360 bytes!)
adding chunk 14 size 1447360bytes in 1394 chunks from total 17 files
addded chunk 14 total output size 21666137bytes in 20654 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 1397 nodes!
content size is 1450006 bytes!)
adding chunk 15 size 1450006bytes in 1397 chunks from total 17 files
addded chunk 15 total output size 23116143bytes in 22051 chunks from total 17 files
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 3 nodes!
content size is 2503 bytes!)
adding chunk 16 size 2503bytes in 3 chunks from total 17 files
addded chunk 16 total output size 23118646bytes in 22054 chunks from total 17 files
header+contents+footer: 23121270b
writeing complete tree
buffered: 23121270b 
saved
```
# ...output3...
```
# java -jar assembly/XMLcompare_assembly_mandatory.jar -orphanSeek -dual -silent -failFast ~/NetBeansProjects/XMLsplitter/test/alpha.xml  ~/NetBeansProjects/XMLjoiner/alpha.xml
checking /home/jvanek/NetBeansProjects/XMLjoiner/alpha.xml
checking /home/jvanek/NetBeansProjects/XMLsplitter/test/alpha.xml
files count: 2
f1: /home/jvanek/NetBeansProjects/XMLjoiner/alpha.xml
f2: /home/jvanek/NetBeansProjects/XMLsplitter/test/alpha.xml
order is important
ignoreContent: false
failFast: true
silent: true
ignoreUri: false
forceTrim: false
ignoreValueCase: false
ignoreNameCase: false
removeSpaces: false
ignoreHeaders: false
dual: true
orphanSeek: true
visualise: false
levenstain is off
validation is off
XMLs are same
```
# ...output1...
```
# java -jar dist/XMLsplitter.jar  -config=test/alpha.conf  -o=/tmp/ -i=test/alpha.xml 
reading: /home/jvanek/NetBeansProjects/XMLsplitter/test/alpha.conf
header xpath setted: /ss:Workbook/o:DocumentProperties | /ss:Workbook/x:ExcelWorkbook | /ss:Workbook/ss:Styles
content xpath setted: /ss:Workbook/ss:Worksheet/ss:Table/ss:Column | /ss:Workbook/ss:Worksheet/ss:Table/ss:Row | /ss:Workbook/ss:Worksheet/x:WorksheetOptions
divSze setted: 16
output file without arg. used working directory
output dir setted: /home/jvanek/NetBeansProjects/XMLsplitter/.
ignoreUri  without arg. Used ALL
input file setted: /home/jvanek/NetBeansProjects/XMLsplitter/test/alpha.xml
output dir setted: /tmp
logging: errorstream
delimiter1 set _part_
delimiter2 set 
header xpath: /ss:Workbook/o:DocumentProperties | /ss:Workbook/x:ExcelWorkbook | /ss:Workbook/ss:Styles
content xpath: /ss:Workbook/ss:Worksheet/ss:Table/ss:Column | /ss:Workbook/ss:Worksheet/ss:Table/ss:Row | /ss:Workbook/ss:Worksheet/x:WorksheetOptions
footer xpath not set
input file: /home/jvanek/NetBeansProjects/XMLsplitter/test/alpha.xml
exists
output directory: /tmp
exists
minsize is not set
maxsize is not set
file will be splitted to: 16 chunks
=======PROCESSING=======
parsing /home/jvanek/NetBeansProjects/XMLsplitter/test/alpha.xml
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
header list have 3 nodes!
header size is 2624 bytes!
gathering namespaces
ss:urn:schemas-microsoft-com:office:spreadsheet
x:urn:schemas-microsoft-com:office:excel
html:http://www.w3.org/TR/REC-html40
o:urn:schemas-microsoft-com:office:office
content list have 22054 nodes!
content size is 23118646 bytes! (~22m)
saving chunk 0 size 1445001bytes in 1363 chunks. Total: 1442377 6%
saving chunk 1 size 1446014bytes in 1354 chunks. Total: 2885767 12%
saving chunk 2 size 1445937bytes in 1367 chunks. Total: 4329080 18%
saving chunk 3 size 1446118bytes in 1389 chunks. Total: 5772574 24%
saving chunk 4 size 1445828bytes in 1398 chunks. Total: 7215778 31%
saving chunk 5 size 1446103bytes in 1375 chunks. Total: 8659257 37%
saving chunk 6 size 1446481bytes in 1370 chunks. Total: 10103114 43%
saving chunk 7 size 1446366bytes in 1389 chunks. Total: 11546856 49%
saving chunk 8 size 1446515bytes in 1372 chunks. Total: 12990747 56%
saving chunk 9 size 1447443bytes in 1377 chunks. Total: 14435566 62%
saving chunk 10 size 1447820bytes in 1373 chunks. Total: 15880762 68%
saving chunk 11 size 1448165bytes in 1370 chunks. Total: 17326303 74%
saving chunk 12 size 1449049bytes in 1380 chunks. Total: 18772728 81%
saving chunk 13 size 1448673bytes in 1383 chunks. Total: 20218777 87%
saving chunk 14 size 1449984bytes in 1394 chunks. Total: 21666137 93%
saving chunk 15 size 1452630bytes in 1397 chunks. Total: 23116143 99%
saving chunk 16 size 5127bytes in 3 chunks Total: 23118646 100%
```
