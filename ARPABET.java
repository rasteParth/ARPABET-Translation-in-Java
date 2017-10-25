import java.io.FileReader;
//import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
public class ARPABET
{
	String word = "";
	String temp = "";
	
	public boolean ARPATranslation(String s, Map dictionary, HashSet<String> memory, String answer, int maxlength, String translation)
	{	
		if (s.length() == 0)
		{
			System.out.println(translation);
			return true;
		} 
		else 
		{
			int index = 0;
			word = "";
			while (index < s.length())
			{					
				word += s.charAt(index);
				if(!temp.contains(word))
				{
					if (memory.contains(word))
					{
						if(ARPATranslation(s.substring(index + 1), dictionary, memory, answer + word + " ", maxlength, translation + dictionary.get(word) + " "))
						{return true;}
					} else
					if (dictionary.containsKey(word))
					{						
							memory.add(word);
							if(ARPATranslation(s.substring(index + 1), dictionary, memory, answer + word + " ", maxlength, translation + dictionary.get(word) + " ")) 
							{				
								return true;
							} 
							else
							{
								index++;
							}			
					} 
					else 
					{	
						if(word.length()<maxlength&&word.length()<s.length())
						{						
							index++;
						}
						else
						{
							answer=answer.trim();
							translation=translation.trim();							
							temp = answer.substring(answer.lastIndexOf(" ")+1);
							s=temp.concat(s);
							index=0 ;							
							answer = answer.replaceAll(" \\S*$", " ");
							translation = translation.replaceAll(" \\S*$", " ");							
							word="";
						}		
					}
				}	
				else
				{
					index++;
				}
			} 
			return false;
		}
	}				
						
	public static void main(String[] args)
	{
		int maxlength=0;
		try{				
				Scanner scandict = new Scanner(new FileReader("dictionary.txt"));
				Map stringMap = new HashMap();
				String[] arr= new String[1000000];
				while(scandict.hasNextLine())
				{
					String s = scandict.nextLine();								
					arr = s.split("\t",2); 
					stringMap.put(arr[0].toString(),arr[1]);				
					if(maxlength<arr[0].length())
					{
						maxlength=arr[0].length();
					}				
				}				
					
				ARPABET ab = new ARPABET();					
				HashSet<String> memory = new HashSet<String>();				
				String s1 = new String(Files.readAllBytes(Paths.get("inputstring.txt")));
				String s2=s1.replaceAll("\\s+", "");	
				ab.ARPATranslation(s2, stringMap, memory, "", maxlength, "");
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

	
