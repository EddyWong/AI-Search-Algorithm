{\rtf1\ansi\ansicpg1252\cocoartf1265
{\fonttbl\f0\fswiss\fcharset0 Helvetica;\f1\fnil\fcharset0 Menlo-Regular;}
{\colortbl;\red255\green255\blue255;}
\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural

\f0\fs24 \cf0 IMPORTANT: MY JAVA VERSION IS 1.7.0_51, I\'92M NOT SURE IF MY PROGRAM CAN RUN ON BELOW THIS VERSION!!!!  \
\pard\tx560\tx1120\tx1680\tx2240\tx2800\tx3360\tx3920\tx4480\tx5040\tx5600\tx6160\tx6720\pardirnatural

\f1\fs22 \cf0 \CocoaLigature0 Java(TM) SE Runtime Environment (build 1.7.0_51-b13)\
Java HotSpot(TM) 64-Bit Server VM (build 24.51-b03, mixed mode)\

\f0\fs24 \CocoaLigature1 \
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural
\cf0 Name: Kang Wang\
USC-ID: 1093294783\
E-mail: kangwang@usc.edu\
\
//Program Structure:\
class search\{\
\
class Person\{\}\
\
Uniform-Cost search function\{\}\
BFS function\{\}\
DFS function\{\}\
findCommunity function\{\}\
\
main function\{\}\
\}\
\
//How to compile and execute\
1.Unzip Kang_Wang.zip\
2. open cmd and locate file path where Kang_Wang.zip was unzipped\
3.input: \'93
\f1\fs22 \CocoaLigature0 java search -t 3 -s A -g F -i input2.txt -t tiebreaking2.txt -op output2_t3_path_cc.txt -ol output2_t3log.txt
\f0\fs24 \CocoaLigature1 \'94\
4.open output file and log file to check\
\
//Output Explanation\
Same as example files\
\
//Addition about readme\
8.1.5.1  I used BFS for task 4\
8.1.5.2  all of these three algorithm are belong to uninformed search. In unweighted graph BFS can return shortest path but DFS can not. Uniform-cost can return optimal path in weighted graph but BFS and DFS can not.\
8.1.5.3 It would return right answer but the log path will be different. time/space would mostly same and sort cost need to be added.\
\
}