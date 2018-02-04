import java.io.*;
import java.util.Base64;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class Sample {

    public static void main(String[] args) throws Exception {
        String str;
        if (args.length == 0) {
            str = "{\"text\":\"Hello stranger\"}";
        } else {
            str = args[0];
        }
        JsonObject jsonArgs = new JsonParser().parse(str).getAsJsonObject();
        System.out.println(main(jsonArgs).toString());
    }

    public static JsonObject main(JsonObject args) throws Exception {
        String text = args.getAsJsonPrimitive("text").getAsString();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream b64os = Base64.getEncoder().wrap(baos);

        BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 300, 300);

        MatrixToImageWriter.writeToStream(matrix, "png", b64os);

        b64os.close();

        String output = baos.toString("utf-8");
        JsonObject response = new JsonObject();
        response.addProperty("qr", output);

        return response;
    }
}

