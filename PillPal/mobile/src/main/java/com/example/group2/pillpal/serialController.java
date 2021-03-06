package com.example.group2.pillpal;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Jessica on 4/30/16.
 */
public class serialController {
    String serializedObject;
    User obj;

    // serialize the object

    public serialController(User u) {
        obj = u;
    }

    public serialController(String s) {
        serializedObject = s;
    }


    public String serialize(Object obj) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(obj);

            StringBuilder sb = new StringBuilder();
//            sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(imageByteArray, false)));
            so.reset();
            serializedObject = bo.toString();

//            ByteArrayOutputStream use = new ByteArrayOutputStream();
//            ObjectOutputStream conv = new ObjectOutputStream(use);
//            conv.writeObject(obj);
//
//            InputStream is = new ByteArrayInputStream(use.toByteArray());
//
////                //This might not be right, output or input to turn into a byte array?
//                ObjectInputStream serialObj = new ObjectInputStream(is);
//                serializedObject = serialObj.toString();


            return serializedObject;
        } catch (Exception e) {
        }
        return serializedObject;
    }


    // deserialize the object
        public User deserialize(String serialObj) {
            try {
                byte b[] = serialObj.getBytes();
                ByteArrayInputStream bi = new ByteArrayInputStream(b);
                ObjectInputStream si = new ObjectInputStream(bi);
                System.out.println(si.toString());
                User u = (User) si.readObject();
                return u;
            } catch (Exception e) {
                System.out.println("deserialproblem:" + e);
            }
            return obj;
        }
}
