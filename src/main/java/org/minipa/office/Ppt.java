package org.minipa.office;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.CryptoFunctions;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.openxmlformats.schemas.presentationml.x2006.main.CTPresentation;
import org.openxmlformats.schemas.presentationml.x2006.main.CTModifyVerifier;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

/**
 * Ppt: 给PPT添加编辑密码
 *
 * @version 0.0.1  @date: 2019-11-25
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>,
 */
public class Ppt {

    public static final String FILE_PATH = "D://XMLSlideShow.pptx";
    public static final String OUT_FILE_PATH = "D://XMLSlideShow-out.pptx";

    public static void main(String[] args) throws Exception {
        createPpt();
//        setPptReadOnly1(FILE_PATH, "123");
//        setPptReadOnly2(FILE_PATH, "123");
//        encryptExcel(FILE_PATH, "123");
    }

    /**
     * 用POI给将PPT设定为只读----可行方法
     *
     * @param filePath
     * @param pwd
     * @throws Exception
     */
    private static void setPptReadOnly1(String filePath, String pwd) throws Exception {
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

    /**
     * 用POI给将PPT设定为只读2
     *
     * @param filePath
     * @param pwd
     * @throws Exception
     */
    private static void setPptReadOnly2(String filePath, String pwd) throws Exception {
        try (POIFSFileSystem fs = new POIFSFileSystem()) {
            EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
            Encryptor enc = info.getEncryptor();
            enc.confirmPassword(pwd);
            try (
                OPCPackage opc = OPCPackage.open(new File(filePath), PackageAccess.READ_WRITE);
                OutputStream os = enc.getDataStream(fs)
            ) {
                opc.save(os);
            }
            // Write out the encrypted version
            try (FileOutputStream fos = new FileOutputStream(OUT_FILE_PATH)) {
                fs.writeFilesystem(fos);
            }
        }
    }

    /**
     * 创建PPT
     */
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

    /**
     * 用POI给Excel文件加密 TODO 待验证正确性 https://stackoverflow.com/questions/47771952/poi-set-password-to-protect-from-changes
     *
     * @param filePath
     * @param pwd
     * @throws Exception
     */
    private static void encryptExcel(String filePath, String pwd) throws Exception {
        POIFSFileSystem fs = new POIFSFileSystem();
        EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
        Encryptor enc = info.getEncryptor();
        enc.confirmPassword(pwd);
        OPCPackage opc = OPCPackage.open(new File(filePath), PackageAccess.READ_WRITE);
        OutputStream os = enc.getDataStream(fs);
        opc.save(os);
        opc.close();

        FileOutputStream fos = new FileOutputStream(filePath);
        fs.writeFilesystem(fos);
        fos.close();
        fs.close();
    }

    /**
     * @param szPassword password hashed using the low-order word algorithm defined in §14.7.1 of ECMA-376
     * @return
     */
    static short getPasswordHash(String szPassword) {
        int wPasswordHash;
        byte[] pch = szPassword.getBytes();
        int cchPassword = pch.length;
        wPasswordHash = 0;
        if (cchPassword > 0) {
            for (int i = cchPassword; i > 0; i--) {
                wPasswordHash = ((wPasswordHash >> 14) & 0x01) | ((wPasswordHash << 1) & 0x7FFF);
                wPasswordHash ^= pch[i - 1];
            }
            wPasswordHash = ((wPasswordHash >> 14) & 0x01) | ((wPasswordHash << 1) & 0x7FFF);
            wPasswordHash ^= cchPassword;
            wPasswordHash ^= (0x8000 | ('N' << 8) | 'K');
        }
        return (short) (wPasswordHash);
    }

}
