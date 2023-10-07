package weShare.sharehappy.service.random;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

@Service
public class RandomStringService {

    public String getRandomCode(){
        //아스키 코드 33 ~ 126로 random 값 설정
        Random random = new Random(System.currentTimeMillis());
        // 33 <= value < 94+33(127)
        char[] list = new char[5];
        for(int i=0;i<5;i++){
            list[i] = (char) (33 + random.nextInt(94));
        }
        return String.valueOf(list);
    }

    public String getRandomPassword(){
        Random random = new Random(System.currentTimeMillis());
        char[] list = new char[10];
        int group1 = 0;
        int group2 = 0;
        int group3 = 0;
        int group4 = 0;
        boolean[] flag = new boolean[3];
        //특수 기호 : 그룹 1, 숫자 : 그룹2, 대문자 : 그룹3, 소문자 : 그룹 4
        for(int i=0;i<10;i++){
            // 그룹 0 ~ 3, 랜덤 그룹 생성
            int group= random.nextInt(4);
            char select = passwordCharacterSelect(random,group);
            list[i] = select;
            if(select == '!' || select == '@'){
                flag[0] = true;
            }
            else if(48 <= select && select <= 57){
                flag[1] = true;
            }
            else if(65 <= select && select <= 90){
                flag[2] = true;
            }
            else{
                flag[2] = true;
            }
        }
        int count = 0;
        for(int i=0;i<flag.length;i++){
            if(!flag[i]){
                count++;
            }
        }
        for(int i=9;i>=10-count;i--){
            for(int j=0;j<flag.length;j++){
                if(!flag[j]){
                    flag[j] = true;
                    list[i] = passwordCharacterSelect(random,j);
                }
            }
        }
        return String.valueOf(list);
    }

    //그룹에 따라 랜덤한 문자를 리턴함.
    private char passwordCharacterSelect(Random random , int group){
        char[] specialSymbol = {'!','@'};
        char result = '1';
        //특수 기호 ! , @
        if(group == 0){
            result = specialSymbol[random.nextInt(2)];
        }
        //숫자 아스키 코드 48 ~ 57
        else if(group == 1){
            result = (char)(48 + random.nextInt(10));

        }
        //대문자 아스키 코드 65 ~ 90
        else if (group == 2){
            result = (char)(65 + random.nextInt(26));

        }
        //소문자 아스키 코드 97 ~ 122
        else{
            result = (char)(97 + random.nextInt(26));
        }
        return result;
    }

    public String getRandomDonationOrderId(Long postId,Long userId){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }


}
