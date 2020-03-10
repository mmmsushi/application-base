import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void encryptPassword(){
        String password="try";
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        System.out.println(encodedPassword);
    }


}
