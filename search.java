import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Person implements Comparable<Person>{
	String name;
	String parentNode=null;
	int group;
	float length;
	public int order;
	HashMap<Person,Float> friends;
	ArrayList<String> friendList;
	public Person(String Pname){
		name=Pname;
		length=0;
		friends = new HashMap<Person,Float>();
		friendList = new ArrayList<String>();
	}
	@Override
	public int compareTo( Person b) {
		// TODO Auto-generated method stub
			if( Math.abs(length - b.length ) > 1e-3 )
				if(length < b.length ) return -1;
				else return 1;
			return 1;
	}
}
class costComparator implements Comparator<Person>{
	public int compare(Person a, Person b){
		if( Math.abs(a.length - b.length ) > 1e-3 )
			if( a.length < b.length ) return -1;
			else return 1;
		return 1;
	}
}

public class search {
static HashMap<String, Person> isExist = new HashMap<String, Person>();
static HashMap<String, Integer> WordOrder = new HashMap<String, Integer>();
private static Scanner scanner,scanner1;
static int count=1;
static int DFSdepth=0;
static HashMap<String, Integer> BFSdepth = new HashMap<String, Integer>();
public static Person getPerson(String name){
	//check if the person has already existed in list
	if(isExist.containsKey(name)){
		return isExist.get(name);
	}
	else{
		Person p = new Person(name);
		isExist.put(name, p);
		return p;
	}
} 	

public static void readInputFile(String filename) throws IOException{//read input file
	FileReader fin=new FileReader(filename);
	 scanner = new Scanner(fin);
	 while(scanner.hasNext()){
		 String str=scanner.next(); 
		 String[] data = str.split(",");
         Person p1 = getPerson(data[0]);
         Person p2 = getPerson(data[1]);
         p1.friends.put(p2, Float.parseFloat(data[2]));
         p2.friends.put(p1, Float.parseFloat(data[2]));
	 }
	 fin.close();
}

public static void readTieFile(String filename) throws IOException{//read tie-breaking file
	WordOrder.clear();
	count=1;
	FileReader fin=new FileReader(filename);
	scanner1 = new Scanner(fin);
	while(scanner1.hasNext()){
		String str=scanner1.next(); 
		WordOrder.put(str, count);
		count++;
	}
	fin.close();
}

public static ArrayList<String> sortbyTieFile(HashMap<String, Integer> order, HashMap<Person, Float> friends){//sort node's friends by alphabetic	
	String[] nameStr = new String[friends.size()];
	int pos = 0;
	Set set=friends.entrySet();
    Iterator it=set.iterator();
    while(it.hasNext()){
        Map.Entry me=(Map.Entry)it.next();
        nameStr[pos++] = ((Person)me.getKey()).name;
    }
    
    for(int i = 0; i < pos-1; i++)
    {
    	for(int j = i+1; j < pos; j++)
    	{
    		if(order.get(nameStr[i]) > order.get(nameStr[j]))
    		{
    			String temp = nameStr[i];
    			nameStr[i] = nameStr[j];
    			nameStr[j] = temp;
    		}
    	}
    }
    
    ArrayList<String> nameList = new ArrayList<String>();
    for(int i = 0; i <  pos; i++)
    {
    	nameList.add(nameStr[i]);
    }
	
	return nameList;
}

public static ArrayList<String> sort(HashMap<String, Integer> order, HashMap<String, Person> input){
	String[] nameStr = new String[input.size()];
	int pos = 0;
	Set set=input.entrySet();
    Iterator it=set.iterator();
    while(it.hasNext()){
        Map.Entry me=(Map.Entry)it.next();
        nameStr[pos++] = (String)me.getKey();
    }
    
    for(int i = 0; i < pos-1; i++)
    {
    	for(int j = i+1; j < pos; j++)
    	{
    		if(order.get(nameStr[i]) > order.get(nameStr[j]))
    		{
    			String temp = nameStr[i];
    			nameStr[i] = nameStr[j];
    			nameStr[j] = temp;
    		}
    	}
    }
    ArrayList<String> nameList = new ArrayList<String>();
    for(int i = 0; i <  pos; i++)
    {
    	nameList.add(nameStr[i]);
    }
	
	return nameList;
}

public static void sortAll(HashMap<String, Person> personList){//sort all node's friends by alphabetic
	Iterator iter = personList.entrySet().iterator();
	while (iter.hasNext()) {
		Map.Entry me=(Map.Entry)iter.next();
		((Person)me.getValue()).friendList=sortbyTieFile(WordOrder,getPerson((String)me.getKey()).friends);
	}
}

public static void Uniformcost(HashMap<String, Person> graph, String startNode, String endNode, String outputfile, String output_log){//Uniform-Cost
	BFSdepth.clear();
	int depth=0;
	Boolean isFind = false;
	String[] path;
	ArrayList<String> record = new ArrayList<String>();
	HashMap<Person,Boolean> visited = new HashMap<Person,Boolean>();
	Iterator iter = graph.entrySet().iterator();
	while (iter.hasNext()) {
		Map.Entry me=(Map.Entry)iter.next();
		visited.put(getPerson((String)me.getKey()), false);
	}
	ArrayList<String> log = new ArrayList<String>();
	Person start = graph.get(startNode);
	Person goal = graph.get(endNode);

	PriorityQueue<Person> q = new PriorityQueue<Person>();
	q.add(start);
	BFSdepth.put(startNode, depth);
//	visited.put(start, true);
	while(!q.isEmpty()&&!isFind){
		Person top = q.poll();
		if( visited.get(top) )
			continue;
		visited.put(top, true);
//		System.out.println(top.name + top.length);
		log.add(top.name);
		if(top.name.equals(endNode)){
			isFind=true;
		}
		Person cur;
		for(int i=0;i<top.friendList.size();i++){
			cur = graph.get(top.friendList.get(i));
			if(!visited.get(cur)){
//				BFSdepth.put(top.friendList.get(i), depth);
				cur.parentNode=top.name;
				BFSdepth.put(cur.name, BFSdepth.get(cur.parentNode)+1);
//				cur.length = curr.parent.length + cost(parnt, cur);
				cur.length= graph.get(cur.parentNode).length + cur.friends.get(graph.get(cur.parentNode));
				//System.out.println("now visiting " + cur.name + " "+ cur.length);
				q.add(cur);
			}
		}
	}
	
  if(isFind){
	int pos=log.indexOf(endNode);
	Person p = goal;
	while(p.parentNode!=null){
		record.add(p.parentNode);
		p = graph.get(p.parentNode);
	}

	path = new String[record.size()+1];
	path[record.size()] = endNode;
	for(int i=record.size()-1;i>=0;i--){
		path[record.size()-i-1]=record.get(i);
	}
	String filename=null,filename_log=null;

		filename=outputfile;
		filename_log=output_log;

	try{
		File f = new File(filename_log);
		File f2 = new File(filename);
		  if (f.exists()&&f2.exists()) {
		        System.out.println("File has existed!");
		       } else {
		        System.out.println("Creating file.....");
		        if (f.createNewFile()&&f2.createNewFile()) {
		         System.out.println(filename+" has been successfully created");
		         System.out.println(filename_log+" has been successfully created");
		         FileWriter writer2=new FileWriter(filename);
		         FileWriter writer=new FileWriter(filename_log);
		        	 //write output file
		        	 for(int i=0;i<path.length;i++){
		        		 writer2.write(path[i]+"\n");
		        	 }
		        	 writer2.close();
		        	//write log file
			         float cur_cost=0;
			         writer.write("name,depth,cost"+"\n");
			         Person cur_person;
			         float cur_l;
			         for(int i=0;i<pos+1;i++){
			        	 cur_l=0;
			        	 cur_person=graph.get(log.get(i));
			        	 while(cur_person.parentNode!=null){
			        		 cur_l+=cur_person.friends.get(graph.get(cur_person.parentNode));
			        		 cur_person=graph.get(cur_person.parentNode);
			        	 }
			        	 graph.get(log.get(i)).length=cur_l;
			        	 writer.write(log.get(i)+","+BFSdepth.get(log.get(i))+","+graph.get(log.get(i)).length+"\n");
			         }
			        	 writer.close();
		        } else {
		         System.out.println("Failure to create file.");
		        }
		       }
	}catch (IOException e){
		e.printStackTrace();
	}
	}
  else{
	  System.out.println("There is no path between "+startNode+" and "+endNode);
  }
	
}

public static boolean DFSvisit(HashMap<String, Person> graph, HashMap<String,Boolean> visited, String startNode, String endNode, String[] path,int depth){
	
	DFSdepth=depth+1;
	if(visited.get(startNode))
		return false;
	visited.put(startNode, true);
	path[depth]=startNode;
	if(startNode.equals(endNode) ){
		return true;
	}
	
	for(int i=0;i<graph.get(startNode).friendList.size();i++)
		if(DFSvisit(graph, visited, graph.get(startNode).friendList.get(i), endNode, path, depth + 1)){
			return true;
		}
	return false;
}

public static void DFSsearch(HashMap<String, Person> graph, String startNode, String endNode, String outputfile, String output_log){//DFS
    DFSdepth=0;
	String[] path = new String[graph.size()];
    String[] noPath = new String[1];
	HashMap<String,Boolean> visited = new HashMap<String,Boolean>();
	Iterator iter = graph.entrySet().iterator();
	while (iter.hasNext()) {
		Map.Entry me=(Map.Entry)iter.next();
		visited.put((String)me.getKey(), false);
	}
    
	if(DFSvisit(graph, visited, startNode, endNode, path, DFSdepth)){	
		String filename=null,filename_log=null;
		
		filename=outputfile;
		filename_log=output_log;

		
		try{
			File f = new File(filename_log);
			File f2 = new File(filename);
			  if (f.exists()&&f2.exists()) {
			        System.out.println("File has existed!");
			       } else {
			        System.out.println("Creating file.....");
			        if (f.createNewFile()&&f2.createNewFile()) {
			         System.out.println(filename+" has been successfully created");
			         System.out.println(filename_log+" has been successfully created");
			         FileWriter writer2=new FileWriter(filename);
			         FileWriter writer=new FileWriter(filename_log);
			         //write log file
			         float cur_cost=0;
			         writer.write("name,depth,cost"+"\n");
			         int j=0;
			         for(int i=0;i<path.length;i++){
				          if(path[i]!=null){
				        	  j++;
				        	  }
				          }
			         for(int i=0;i<j;i++){		
			        	if(i==j-1){
			        		writer.write(path[i]+","+i+","+cur_cost+"\n");
			        	}else{
			        		 writer.write(path[i]+","+i+","+cur_cost+"\n");
				        	 cur_cost+=graph.get(path[i]).friends.get(graph.get(path[i+1]));
			        	}			          
			         }
			        	 writer.close();
			        	 //write output file
			        	 for(int i=0;i<path.length;i++){
			        		 if(path[i]!=null){
			        		 writer2.write(path[i]+"\n");
			        		 }
			        	 }
			        	 writer2.close();
			        } else {
			         System.out.println("Failure to create file.");
			        }
			       }
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	else{
		System.out.println("There is no path between "+startNode+" and "+endNode);
	}
}

public static void BFSsearch(HashMap<String, Person> graph, String startNode, String endNode, String outputfile, String output_log){//BFS
	BFSdepth.clear();
	int depth=0;
	Boolean isFind = false;
	String[] path;
	ArrayList<String> record = new ArrayList<String>();
	HashMap<Person,Boolean> visited = new HashMap<Person,Boolean>();
	Iterator iter = graph.entrySet().iterator();
	while (iter.hasNext()) {
		Map.Entry me=(Map.Entry)iter.next();
		visited.put(getPerson((String)me.getKey()), false);
	}
	ArrayList<String> log = new ArrayList<String>();
	Person start = graph.get(startNode);
	Person goal = graph.get(endNode);

	Queue<Person> q = new ArrayDeque();
	q.add(start);
	BFSdepth.put(startNode, depth);
	log.add(startNode);
	visited.put(start, true);
	while(!q.isEmpty()&&!isFind){
		Person top = q.poll();
//		if(!visited.get(top)) {depth++;}
		for(int i=0;i<top.friendList.size();i++){
			if(!visited.get(graph.get(top.friendList.get(i)))){
//				BFSdepth.put(top.friendList.get(i), depth);
				log.add(top.friendList.get(i));
				visited.put(graph.get(top.friendList.get(i)), true);
				graph.get(top.friendList.get(i)).parentNode=top.name;
				BFSdepth.put(top.friendList.get(i), BFSdepth.get(getPerson(top.friendList.get(i)).parentNode)+1);
				q.add(graph.get(top.friendList.get(i)));
				if(top.friendList.get(i).equals(endNode)){
					isFind=true;
				}
			}
		}
	}
	
  if(isFind){
	int pos=log.indexOf(endNode);
	Person p = goal;
	while(p.parentNode!=null){
		record.add(p.parentNode);
		p = graph.get(p.parentNode);
	}

	path = new String[record.size()+1];
	path[record.size()] = endNode;
	for(int i=record.size()-1;i>=0;i--){
		path[record.size()-i-1]=record.get(i);
	}
	String filename=null,filename_log=null;
	
	filename=outputfile;
	filename_log=output_log;
	
	try{
		File f = new File(filename_log);
		File f2 = new File(filename);
		  if (f.exists()&&f2.exists()) {
		        System.out.println("File has existed!");
		       } else {
		        System.out.println("Creating file.....");
		        if (f.createNewFile()&&f2.createNewFile()) {
		         System.out.println(filename+" has been successfully created");
		         System.out.println(filename_log+" has been successfully created");
		         FileWriter writer2=new FileWriter(filename);
		         FileWriter writer=new FileWriter(filename_log);
		        	 //write output file
		        	 for(int i=0;i<path.length;i++){
		        		 writer2.write(path[i]+"\n");
		        	 }
		        	 writer2.close();
		        	//write log file
			         float cur_cost=0;
			         writer.write("name,depth,cost"+"\n");
			         Person cur_person;
			         float cur_l;
			         for(int i=0;i<pos+1;i++){
			        	 cur_l=0;
			        	 cur_person=graph.get(log.get(i));
//			        	 System.out.println("Now: "+cur_person.name);
			        	 while(cur_person.parentNode!=null){
//			        		 System.out.println(cur_person.name+" parent: "+ cur_person.parentNode);
			        		 cur_l+=cur_person.friends.get(graph.get(cur_person.parentNode));
			        		 cur_person=graph.get(cur_person.parentNode);
			        	 }
			        	 graph.get(log.get(i)).length=cur_l;
//			        	 System.out.println(graph.get(log.get(i)).length);
			        	 writer.write(log.get(i)+","+BFSdepth.get(log.get(i))+","+graph.get(log.get(i)).length+"\n");
			         }
			        	 writer.close();
		        } else {
		         System.out.println("Failure to create file.");
		        }
		       }
	}catch (IOException e){
		e.printStackTrace();
	}
	}
  else{
	  System.out.println("There is no path between "+startNode+" and "+endNode);
  }
	
}


public static ArrayList<String> BFS(String startNode, HashMap<String, Person> graph, HashMap<String,Boolean> visited, ArrayList<String> path, int group){
	int depth=0;
//	Boolean isFind = false;
	Queue<String> q = new ArrayDeque();
	q.add(startNode); //add into queue
	BFSdepth.put(startNode, depth); //record depth
	visited.put(startNode, true); // mark node visited
	graph.get(startNode).group=group; //set group number
	path.add(startNode); //Add startNode into output
	while(!q.isEmpty()){
		String top = q.poll();
		String cur;
		for(int i=0; i<graph.get(top).friendList.size();i++){
			cur = graph.get(top).friendList.get(i);
			if(!visited.get(cur)){
				visited.put(cur, true);
				graph.get(cur).group=group;
				path.add(cur);
				graph.get(cur).parentNode=top;
				BFSdepth.put(cur, BFSdepth.get(graph.get(cur).parentNode)+1);
				q.add(cur);
			}
		}
	}
	
	return path;
}

public static void findCommunity(HashMap<String, Person> graph, ArrayList<String> order, String outputfile, String output_log) throws IOException{//Find communities 
	int group=0;
	BFSdepth.clear();
	ArrayList path = new ArrayList<String>();
	HashMap<String,Boolean> visited = new HashMap<String,Boolean>();
	for(int i=0;i<order.size();i++){
		visited.put(order.get(i), false);
	}
	for(int i=0;i<order.size();i++){
		if(!visited.get(order.get(i))){
			group++;
			path=BFS(order.get(i),graph, visited, path, group);
		}
	}
	
	String filename=null,filename_log=null;
	
	filename=outputfile;
	filename_log=output_log;
	
	try{
		File f = new File(filename_log);
		File f2 = new File(filename);
		  if (f.exists()&&f2.exists()) {
		        System.out.println("File has existed!");
		       } else {
		        System.out.println("Creating file.....");
		        if (f.createNewFile()&&f2.createNewFile()) {
			     System.out.println(filename+" has been successfully created");
			     System.out.println(filename_log+" has been successfully created");
		         FileWriter writer2=new FileWriter(filename);
		         FileWriter writer=new FileWriter(filename_log);
		         //write log file
		         writer.write("name,depth,group"+"\n");
		        	 for(int j=0;j<path.size();j++){
		        		 if(j==path.size()-1){
		        		 writer.write(path.get(j)+","+BFSdepth.get(path.get(j))+","+graph.get(path.get(j)).group+"\n");
		        		 writer.write("---------------------"+"\n");
		        		 }
		        		 else{
		        			 if(graph.get(path.get(j+1)).group>graph.get(path.get(j)).group){
		        				writer.write(path.get(j)+","+BFSdepth.get(path.get(j))+","+graph.get(path.get(j)).group+"\n");
		        				writer.write("---------------------"+"\n");
		        			 }
		        			 else{
		        				writer.write(path.get(j)+","+BFSdepth.get(path.get(j))+","+graph.get(path.get(j)).group+"\n"); 
		        			 }
		        		 }		        		 
		        	 }
		        	 writer.close();
		        	 //write output file
		        	 for(int j=0;j<path.size();j++){
		        		 if(j==path.size()-1){
		        			 writer2.write(path.get(j)+"\n");
		        		 }else{
		        			 if(graph.get(path.get(j+1)).group>graph.get(path.get(j)).group){
		        				 writer2.write(path.get(j)+"\n");
		        			 }else{
		        				 writer2.write(path.get(j)+",");
		        			 }
		        		 }
		        	 }
		        	 writer2.close();
		        } else {
		         System.out.println("Failure to create file.");
		        }
		       }
	}catch (IOException e){
		e.printStackTrace();
	}
	
}

 public static void main(String args[]) throws IOException{//main function

     String start = "";
     String goal = "";
     String outputtext = "";
     String outputlog = "";
     String input = "";
     String tiebreak = "";
     int task = 0;
     int i = 0;
     while (i < args.length) {
         if (args[i].equals("-t")) {
             i++;
             if (args[i].equals("1")) {
                 task = 1;
             } else if (args[i].equals("2")) {
                 task = 2;
             } else if (args[i].equals("3")) {
                 task = 3;
             } else if (args[i].equals("4")) {
                 task = 4;
             } else {
                 tiebreak = args[i];
             }
         }
         if (args[i].equals("-s")) {
             i++;
             start = args[i];
         }
         if (args[i].equals("-g")) {
             i++;
             goal = args[i];
         }
         if (args[i].equals("-i")) {
             i++;
             input = args[i];
         }
         if (args[i].equals("-op")) {
             i++;
             outputtext = args[i];
         }
         if (args[i].equals("-ol")) {
             i++;
             outputlog = args[i];
         }
         i++;
	 }
     
     if(task==1){
    	 readInputFile(input);
    	 readTieFile(tiebreak);    	 	 
    	 sortAll(isExist);
    	 BFSsearch(isExist, start, goal, outputtext, outputlog);
     }
     else if(task==2){
    	 readInputFile(input);
    	 readTieFile(tiebreak);    	 	 
    	 sortAll(isExist);
    	 DFSsearch(isExist, start, goal, outputtext, outputlog);
     }
     else if(task==3){
    	 readInputFile(input);
    	 readTieFile(tiebreak);   	 	 
    	 sortAll(isExist);
    	 Uniformcost(isExist, start, goal, outputtext, outputlog);
     }
     else if(task==4){
    	 readInputFile(input);
    	 readTieFile(tiebreak);    	 	 
    	 sortAll(isExist);
    	 ArrayList<String> name = sort(WordOrder,isExist);
    	 findCommunity(isExist, name, outputtext, outputlog);
     }

 }

}

