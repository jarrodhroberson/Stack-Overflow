import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class Scratch
{
    public static void main(String[] args) throws IOException
    {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final CharBuffer cb = CharBuffer.allocate(1024);
        final StringBuilder sb = new StringBuilder(1024);
        while (true)
        {
            if (br.read(cb) != -1)
            {
                cb.flip();
                for (int i=0; i < cb.limit(); i++)
                {
                    if (i == cb.limit()-1 || cb.get(i) == ',')
                    {
                        sb.append(cb,0,i);
                        break;
                    }
                }
                System.out.println(sb.toString());
            }
        }
    }
}
