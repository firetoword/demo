package com.example.cong.controller;

import com.example.cong.service.TestService;
import com.itextpdf.text.pdf.BaseFont;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/cong")
public class IndexController {

    @Autowired
    private TestService testService;

    @RequestMapping("/index")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/getTest")
    public String getTest() throws Exception {
        try {
            InputStream in = new FileInputStream(new File("D:\\点的点能源零售业务系统-新-打标签.docx"));//要转化的word
            XWPFDocument document = new XWPFDocument(in);
            OutputStream baos = new ByteArrayOutputStream();
            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
            xhtmlConverter.convert(document, baos,null);

            ByteArrayOutputStream baos1 = (ByteArrayOutputStream) baos;
            InputStream swapStream = new ByteArrayInputStream(baos1.toByteArray());

            String content = baos.toString();//转化好的html代码
            content = content.replaceAll("&ldquo;","").replaceAll("&rdquo;","").replaceAll("&times;","")
                    .replace("<body>","<body style=\"font-family: SimSun;\">")
                    .replace("</head>","<style type=\"text/css\">body {font-family: SimSun;}</style></head>")
                    .replace("margin-left:79.4pt;margin-right:73.7pt","margin-left:auto;margin-right:auto");
//            createPdf(content);
            String  a = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n";
            a += content;
            html2pdf(a);
            baos.close();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "content";

    }

    public static void html2pdf(String htmlFile)  {
        OutputStream os = null;
        try {
            os = new FileOutputStream("D://htmltest.pdf");
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlFile);
            // 解决中文不显示问题
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("D:\\jar\\simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            fontResolver.addFont("D:\\jar\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
        } catch (Exception e) {
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }
    /*public void createPdf(String content) {
        try {
            //BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
            //直接引用 下载好的字体文件
            BaseFont bfChinese = BaseFont.createFont("D:\\jar\\simsun.ttf", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

            Font font = new Font(bfChinese, 12, Font.NORMAL);
            XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontImp.register("D:\\jar\\simsun.ttf");
            Document document = new Document(PageSize.A4, 10, 10, 10, 10);
            PdfWriter mPdfWriter = PdfWriter.getInstance(document, new FileOutputStream("D://htmltest.pdf"));
            document.open();

//            document.add(new Paragraph(content, font));

//            ByteArrayInputStream bin = new ByteArrayInputStream(content.getBytes("UTF-8"));
//            XMLWorkerHelper.getInstance().parseXHtml(mPdfWriter, document, bin, Charset.forName("UTF-8"));
//            XMLWorkerHelper.getInstance().parseXHtml(mPdfWriter, document,
//                    new FileInputStream("index.html"));
//            XMLWorkerHelper.getInstance().parseXHtml(mPdfWriter,document, content);
            InputStream bin = new ByteArrayInputStream(content.getBytes());
//            XMLWorkerHelper.getInstance().parseXHtml(mPdfWriter, document, bin,null, Charset.forName("UTF-8"), new ChinaFontProvide());
            XMLWorkerHelper.getInstance().parseXHtml(mPdfWriter, document, bin);
            document.close();
            mPdfWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getHtml() {

        StringBuffer html = new StringBuffer();
        html.append("<div style='color:green;font-size:20px;'>你好世界！hello world !</div>");
        html.append("<span style='color:red'>what are you 弄啥咧!</span>");
        html.append("<h1>标题</h1>");
        html.append("<table>");
        html.append("<tr>");
        html.append("<th>序号</th>");
        html.append("<th>用户名</th>");
        html.append("<th>性别</th>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<td>1</td>");
        html.append("<td>fengxing</td>");
        html.append("<td>男</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<td>2</td>");
        html.append("<td>admin</td>");
        html.append("<td>女</td>");
        html.append("</tr>");


        html.append("</table>");
        return html.toString();
    }
    public static final class ChinaFontProvide implements FontProvider {

        @Override
        public Font getFont(String arg0, String arg1, boolean arg2, float arg3,
                            int arg4, BaseColor arg5) {
            BaseFont bfChinese = null;
            try {
                //bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                //直接引用 下载好的字体文件
                bfChinese = BaseFont.createFont("D:\\jar\\simsun.ttf", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
            return FontChinese;
        }

        @Override
        public boolean isRegistered(String arg0) {
            return false;
        }
    }



//====================================================

        *//**
         * 重写 字符设置方法，解决中文乱码问题
         *//*
        public static class MyFontsProvider extends XMLWorkerFontProvider {
            @Override
            public Font getFont(final String fontname, final String encoding, final boolean embedded, final float size, final int style, final BaseColor color) {
                BaseFont bf = null;
                try {
                    bf = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                } catch (Exception e) {
                }
                Font font = new Font(bf, size, style, color);
                font.setColor(color);
                return font;
            }
        }

        *//**
         * PDF生成路径
         *//*
        public static final String PDF_DOWNLOAD_PATH = File.separator +"tempDir"+File.separator;

        *//**
         * 导出PDF文件
         *
         * @param content
         * @param response
         *//*
        public static void exportPdf(String fileName, String content, HttpServletResponse response) {

            FileOutputStream fos = null;
            FileInputStream in = null;
            OutputStream out = null;
            Document document = new Document();
            File newPath = null;
            try {
                if (StringUtils.isEmpty(fileName)) {
                    fileName = "123";
                }
                fileName+=".pdf";
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                String dicPath = new File(".").getCanonicalPath();
                String srcPath = dicPath + PDF_DOWNLOAD_PATH + fileName;

                newPath = new File(dicPath + PDF_DOWNLOAD_PATH);
                newPath.mkdirs();
                // 删除临时文件
                boolean success = fileDelete(newPath);

                if (success) {
                    newPath.mkdirs();
                    File file = new File(srcPath);
                    fos = new FileOutputStream(file);

                    PdfWriter writer = PdfWriter.getInstance(document, fos);

                    document.open();
                    InputStream htmlInput = new ByteArrayInputStream(content.getBytes("UTF-8"));
                    // 使用我们的字体提供器，并将其设置为unicode字体样式
                    MyFontsProvider fontProvider = new MyFontsProvider();
                    fontProvider.addFontSubstitute("lowagie", "garamond");
                    fontProvider.setUseUnicode(true);
                    CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
                    HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
                    htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
                    XMLWorkerHelper.getInstance().getDefaultCssResolver(true);

                    XMLWorkerHelper.getInstance().parseXHtml(writer, document, htmlInput, null, Charset.forName("UTF-8"),
                            fontProvider);

                    document.close();
                    writer.close();
                    // 设置文件ContentType类型，这样设置，会自动判断下载文件类型
                    response.setContentType("multipart/form-data");
                    // 设置响应头，控制浏览器下载该文件
                    response.setHeader("content-disposition", "attachment;filename=" + fileName);
                    // 读取要下载的文件，保存到文件输入流
                    in = new FileInputStream(srcPath);
                    // 创建输出流
                    out = response.getOutputStream();
                    // 创建缓冲区
                    byte buffer[] = new byte[1024];
                    int len = 0;
                    // 循环将输入流中的内容读取到缓冲区当中
                    while ((len = in.read(buffer)) > 0) {
                        // 输出缓冲区的内容到浏览器，实现文件下载
                        out.write(buffer, 0, len);
                    }
                }
            } catch (DocumentException e) {
                throw new RuntimeException("Export PDF error : ", e);
            } catch (IOException e) {
                throw new RuntimeException("Export PDF error : ", e);
            } catch (Exception e) {
                throw new RuntimeException("Export PDF error : ", e);
            } finally {
                IOUtils.closeQuietly(fos);
                IOUtils.closeQuietly(in);
                IOUtils.closeQuietly(out);
                if (newPath != null) {
                    fileDelete(newPath);
                }
            }
        }

        *//**
         * 删除文件
         *
         * @param file
         * @return
         *//*
        private static boolean fileDelete(File file) {
            if (file.isDirectory()) {
                String[] children = file.list();
                // 递归删除目录中的子目录下
                for (int i = 0; i < children.length; i++) {
                    boolean success = fileDelete(new File(file, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
            // 目录此时为空，可以删除
            return file.delete();
        }*/

}
