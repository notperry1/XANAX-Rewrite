package cat.yoink.xanax.internal.util;

import java.io.*;

/**
 * @author yoink
 */
public final class FileUtil
{
    public static void saveFile(File file, String content)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getContents(File file)
    {
        StringBuilder builder = new StringBuilder();
        try
        {
            FileInputStream stream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(stream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = br.readLine()) != null) builder.append(line);

            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
