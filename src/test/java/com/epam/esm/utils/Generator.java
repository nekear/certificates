package com.epam.esm.utils;

import com.epam.esm.web.entities.Certificate;
import com.epam.esm.web.entities.Tag;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Generator {
    public static final Random random = new Random();

    public static Certificate genCertificate(boolean generateTags){
        Certificate certificate = new Certificate();

        certificate.setName(generateRandomString(10));
        certificate.setDescription(generateRandomString(30));
        certificate.setPrice(random.nextInt(1980)+20);
        certificate.setDuration(random.nextInt(360)+5);

        if(generateTags){
            int tagsAmount = random.nextInt(3) + 1;
            List<Tag> tags = new LinkedList<>();
            for(int i = 0; i < tagsAmount; i++)
                tags.add(genTag());

            certificate.setTags(tags);
        }

        return certificate;
    }

    public static Tag genTag(){
        Tag tag = new Tag();
        tag.setName(generateRandomString(6));

        return tag;
    }

    public static String generateRandomString(int bound){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = bound;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextDouble() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
