import java.util.*;
import java.io.*;

public class Decoder {
    public static void decode(String inputFile, String outputFile) throws IOException
	{
		FileInputStream in = new FileInputStream(inputFile);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
		StringBuilder binary = new StringBuilder();
		byte[] array = in.readAllBytes();
		
		for(int i = 0; i < array.length; i++)
		{
			binary.append(toBinary(array[i]));
		}
		
		System.out.println(binary);
		
		ArrayList<Integer> binaryValues = new ArrayList<Integer>();
		
		for(int i = 0 ;i <= binary.length() - 9; i += 9)
		{
			binaryValues.add(toBinaryValue(binary.substring(i, i+9)));
		}
		System.out.println(binaryValues);
		
		HashMap<Integer, String> map = new HashMap<Integer,String>();
		for(int i = 0; i < 256; i++)
			map.put(i, "" + (char)i);
		
		int nextValue = 256;
		int current = binaryValues.get(0);
		String str = map.get(current);
		String ch = "" + str.charAt(0);
		out.print(str);
		for(int i = 1; i < binaryValues.size(); i++)
		{
			int next = binaryValues.get(i);
			if(!map.containsKey(next))
			{
				str = map.get(current);
				str = str + ch;
			}
			else
			{
				str = map.get(next);
			}
			out.print(str);
			ch = "" + str.charAt(0);
			map.put(nextValue, map.get(current) + ch);
			nextValue++;
			current = next;
		}
		
		out.println();
		
		out.close();
	}

    public static String toBinary(int num)
	{
		String current = Integer.toBinaryString(num);
		StringBuilder str = new StringBuilder();
		while(current.length()+str.length() < 8)
		{
			str.append("0");
		}
		if(current.length() > 8)
		{
			current = current.substring(current.length() - 8);
		}
		str.append(current);
		return str.toString();
	}

    public static int toBinaryValue(String str)
	{
		int end = 0;
		for(int i = 0; i < 9; i++)
		{
			if(str.charAt(i) == '1')
			{
				end += (1<<(8-i));
			}
		}
		return end;
	}

    public static void main (String [] args) throws IOException
    {
        decode("encoded2.bin", "decoded.txt");
    }
}
