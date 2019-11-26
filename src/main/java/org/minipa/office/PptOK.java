package org.minipa.office;

import org.apache.poi.poifs.crypt.CryptoFunctions;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.openxmlformats.schemas.presentationml.x2006.main.CTModifyVerifier;
import org.openxmlformats.schemas.presentationml.x2006.main.CTPresentation;

import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

/**
 * Ppt: 给PPT添加编辑密码
 *
 * @version 0.0.1  @date: 2019-11-25
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>,
 */
public class PptOK {

    public static final String FILE_PATH = "D://XMLSlideShow.pptx";
    public static final String OUT_FILE_PATH = "D://XMLSlideShow-out.pptx";

    public static void main(String[] args) throws Exception {
        createPpt();
        setPptReadOnly(FILE_PATH, "123");
    }

    /**
     * 用POI给将PPT设定为只读
     *
     * @param filePath
     * @param pwd
     * @throws Exception
     */
    private static void setPptReadOnly(String filePath, String pwd) throws Exception {
        XMLSlideShow slideShow = new XMLSlideShow(new FileInputStream(FILE_PATH));
        CTPresentation ctpresentation = slideShow.getCTPresentation();
        CTModifyVerifier ctmodifyverifier = ctpresentation.getModifyVerifier();
        if (ctmodifyverifier == null) {
            ctmodifyverifier = ctpresentation.addNewModifyVerifier();
        }

        ctmodifyverifier.setCryptProviderType(org.openxmlformats.schemas.presentationml.x2006.main.STCryptProv.RSA_FULL);
        ctmodifyverifier.setCryptAlgorithmClass(org.openxmlformats.schemas.presentationml.x2006.main.STAlgClass.HASH);
        ctmodifyverifier.setCryptAlgorithmType(org.openxmlformats.schemas.presentationml.x2006.main.STAlgType.TYPE_ANY);
        ctmodifyverifier.setCryptAlgorithmSid(4); //SHA-1
        ctmodifyverifier.setSpinCount(100000);

        SecureRandom random = new SecureRandom();
        byte[] salt = random.generateSeed(16);
        byte[] hash = CryptoFunctions.hashPassword(pwd, HashAlgorithm.sha1, salt, 100000, false);

        ctmodifyverifier.setHashData(java.util.Base64.getEncoder().encodeToString(hash));
        ctmodifyverifier.setSaltData(java.util.Base64.getEncoder().encodeToString(salt));

        slideShow.write(new FileOutputStream(OUT_FILE_PATH));
        slideShow.close();
    }

    private static void createPpt() {
        SlideShow ppt = new XMLSlideShow();
        try {
            Slide slide = ppt.createSlide();

            XSLFTextBox textBox = (XSLFTextBox) slide.createTextBox();
            textBox.setAnchor(new Rectangle2D.Double(10, 10, 0, 0));
            textBox.addNewTextParagraph().addNewTextRun().setText("创建幻灯片2");

            FileOutputStream out = new FileOutputStream(FILE_PATH);
            ppt.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
