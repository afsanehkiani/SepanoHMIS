/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jj;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import cms.tools.ServerLog;
/**
 *
 * @author Milad
 */
public class jjUpload {

    /**
     * Upload a file to a FTP server. A FTP URL is generated with the
     * following syntax:
     * ftp://user:password@host:port/filePath;type=i.
     *
     * @param ftpServer , FTP server address (optional port ':portNumber').
     * @param user , Optional user name to login.
     * @param password , Optional password for user.
     * @param fileName , Destination file name on FTP server (with optional
     *            preceding relative path, e.g. "myDir/myFile.txt").
     * @param source , Source file to upload.
     * @throws MalformedURLException, IOException on error.
     */
    public void doUploadFileToServer(String ftpServer, String user, String password,
            String fileName, File source) throws Exception {
        if (ftpServer != null && fileName != null && source != null) {
            StringBuffer sb = new StringBuffer("ftp://");
            // check for authentication else assume its anonymous access.
            if (user != null && password != null) {
                sb.append(user);
                sb.append(':');
                sb.append(password);
                sb.append('@');
            }
            sb.append(ftpServer);
            sb.append('/');
            sb.append(fileName);
            /*
             * type ==> a=ASCII mode, i=image (binary) mode, d= file directory
             * listing
             */
            sb.append(";type=i");

            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                URL url = new URL(sb.toString());
                URLConnection urlc = url.openConnection();

                bos = new BufferedOutputStream(urlc.getOutputStream());
                bis = new BufferedInputStream(new FileInputStream(source));

                int i;
                // read byte by byte until end of stream
                while ((i = bis.read()) != -1) {
                    bos.write(i);
                }
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (Exception ioe) {
                        ioe.printStackTrace();
                    }
                }
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (Exception ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        } else {
            ServerLog.Print("Input not available.");
        }
    }

    /**
     * Download a file from a FTP server. A FTP URL is generated with the
     * following syntax:
     * ftp://user:password@host:port/filePath;type=i.
     *
     * @param ftpServer , FTP server address (optional port ':portNumber').
     * @param user , Optional user name to login.
     * @param password , Optional password for user.
     * @param fileName , Name of file to download (with optional preceeding
     *            relative path, e.g. one/two/three.txt).
     * @param destination , Destination file to save.
     * @throws MalformedURLException, IOException on error.
     */
    public void doDownloadFileFromServer(String ftpServer, String user, String password,
            String fileName, File destination) throws Exception {
        if (ftpServer != null && fileName != null && destination != null) {
            StringBuffer sb = new StringBuffer("ftp://");
            // check for authentication else assume its anonymous access.
            if (user != null && password != null) {
                sb.append(user);
                sb.append(':');
                sb.append(password);
                sb.append('@');
            }
            sb.append(ftpServer);
            sb.append('/');
            sb.append(fileName);
            /*
             * type ==> a=ASCII mode, i=image (binary) mode, d= file directory
             * listing
             */
            sb.append(";type=i");
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                URL url = new URL(sb.toString());
                URLConnection urlc = url.openConnection();

                bis = new BufferedInputStream(urlc.getInputStream());
                bos = new BufferedOutputStream(new FileOutputStream(
                        destination.getName()));

                int i;
                while ((i = bis.read()) != -1) {
                    bos.write(i);
                }
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (Exception ioe) {
                        ioe.printStackTrace();
                    }
                }
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (Exception ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        } else {
            ServerLog.Print("Input not available");
        }
    }
}
