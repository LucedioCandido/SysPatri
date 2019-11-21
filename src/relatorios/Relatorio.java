package relatorios;

import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import conexaoBD.Conexao;
import conexaoBD.ConexaoBem;
import conexaoBD.excecoesBD.AbsenceDriverMSQLException;
import conexaoBD.excecoesBD.DatabaseAccessException;
import conexaoBD.excecoesBD.InvalidInputParametersException;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Relatorio extends Conexao {


    public void exportData() throws DatabaseAccessException, AbsenceDriverMSQLException, DocumentException {
        try {
            conectar();
            //documento .pdf do Itext
            Document document = new Document();
            Date data = new Date();
            SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
            String Data = form.format(data);
            FileOutputStream ou = new FileOutputStream("rel.pdf");
                PdfWriter.getInstance(document, ou);
                document.open(); // Abrindo o documento

                //gerando o cabeçalho do arquivo
                document = genereteCabecalhoReport(document);

                //Parte do titulo da tabela.
                PdfPTable tableBens = generateTable();

                //adiciona os dados do BD à tabela
                tableBens = readDataBase(tableBens);

                // add a tabela ao relatorio em pdf
                document.add(tableBens);

                try {
                    Desktop.getDesktop().open(new File("rel.pdf")); // pega a referência do PDF na pasta raiz para abri-lo.
                } catch (IOException e) {
                    //erro ao abrir aquivo
                } finally {
                    document.close(); // Fecha  documento
                }
        } catch (FileNotFoundException | InvalidInputParametersException | SQLException e) {
            e.printStackTrace();
        }
    }

    private Document genereteCabecalhoReport(Document doc) throws DocumentException {
        //cabeçalho do relatorio
        Font smallfont = new Font(Font.FontFamily.HELVETICA, 13);
        Font ff = new Font(Font.FontFamily.COURIER, 14, Font.BOLD + Font.ITALIC + Font.UNDERLINE);
        Font fontTitle = new Font(Font.FontFamily.COURIER, 13, Font.BOLD + Font.ITALIC);

        Paragraph titulo = new Paragraph(" Sistema Patrimonial", fontTitle);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        doc.add(titulo);

        Paragraph p = new Paragraph("                                                             ", ff);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setSpacingAfter(30);
        doc.add(p);

        Paragraph p1 = new Paragraph("Relação de todos os bens cadastrados", smallfont);
        p1.setAlignment(Element.ALIGN_CENTER);
        doc.add(p1);
        Paragraph p10 = new Paragraph("                                            ");
        doc.add(p10);

        return doc;
    }

    private PdfPTable generateTable() throws DocumentException {

        // Tabela
        PdfPTable table = new PdfPTable(5);
        //@TO DO a definicao do tamnahjo variavel
        table.setTotalWidth(500);

        table.setLockedWidth(true);
        table.setWidths(new int[]{10, 8, 5, 8, 8, 5, 12});

        Font fontTableTitle = new Font(Font.FontFamily.HELVETICA, 9);
        Font fontTable = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(BaseColor.WHITE.getRGB()));

        // Células do codigo
        PdfPCell cod_bem = new PdfPCell(new Phrase("Código", fontTableTitle));
        cod_bem.setColspan(7);
        cod_bem.setHorizontalAlignment(Element.ALIGN_CENTER);
        cod_bem.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cod_bem.setBorder(cod_bem.NO_BORDER);
        cod_bem.setBackgroundColor(new BaseColor(102, 102, 102));
        cod_bem.setPaddingTop(5);
        cod_bem.setPaddingBottom(6);
        table.addCell(cod_bem);

        // Células do nome
        PdfPCell nome_bem = new PdfPCell(new Phrase("Nome", fontTable));
        nome_bem.setBorder(nome_bem.NO_BORDER);
        nome_bem.setHorizontalAlignment(Element.ALIGN_CENTER);
        nome_bem.setVerticalAlignment(Element.ALIGN_MIDDLE);
        nome_bem.setBackgroundColor(new BaseColor(255, 176, 0));
        nome_bem.setPaddingBottom(5);
        nome_bem.setPaddingTop(4);

        // Célula de desccricao
        PdfPCell descricao = new PdfPCell(new Phrase("Descrição", fontTable));
        descricao.setBorder(descricao.NO_BORDER);
        descricao.setHorizontalAlignment(Element.ALIGN_CENTER);
        descricao.setVerticalAlignment(Element.ALIGN_MIDDLE);
        descricao.setBackgroundColor(new BaseColor(255, 176, 0));
        descricao.setPaddingTop(4);
        descricao.setPaddingBottom(5);

        // Células da categoria
        PdfPCell categoria = new PdfPCell(new Phrase("Categoria", fontTable));
        categoria.setBorder(categoria.NO_BORDER);
        categoria.setHorizontalAlignment(Element.ALIGN_CENTER);
        categoria.setVerticalAlignment(Element.ALIGN_MIDDLE);
        categoria.setBackgroundColor(new BaseColor(255, 176, 0));
        categoria.setPaddingTop(4);
        categoria.setPaddingBottom(5);


        // Células da localizacao
        PdfPCell local = new PdfPCell(new Phrase("Localização", fontTable));
        local.setBorder(local.NO_BORDER);
        local.setHorizontalAlignment(Element.ALIGN_CENTER);
        local.setVerticalAlignment(Element.ALIGN_MIDDLE);
        local.setBackgroundColor(new BaseColor(255, 176, 0));
        local.setPaddingTop(4);
        local.setPaddingBottom(5);


        // Adiciona todas as células criadas na tabela
        table.addCell(cod_bem);
        table.addCell(nome_bem);
        table.addCell(descricao);
        table.addCell(categoria);
        table.addCell(local);

        return table;
    }

    private PdfPTable readDataBase(PdfPTable tableCabecalho) throws InvalidInputParametersException, SQLException {
        ConexaoBem conexaoBem = new ConexaoBem();
        ResultSet resultado =  conexaoBem.gerarRelatorio();
        Font fontTable = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(BaseColor.WHITE.getRGB()));
        while (resultado.next()) {
            // Adicionando célula com os dados do banco
            PdfPCell cod = new PdfPCell(new Phrase(resultado.getString("cod_bem"), fontTable));
            cod.setPadding(5);
            cod.setBorder(cod.NO_BORDER);
            cod.setHorizontalAlignment(Element.ALIGN_CENTER);
            cod.setVerticalAlignment(Element.ALIGN_MIDDLE);

            // Adicionando célula com os dados do banco
            PdfPCell Descricao = new PdfPCell(new Phrase(resultado.getString("descricao"), fontTable));
            Descricao.setPadding(5);
            Descricao.setBorder(Descricao.NO_BORDER);
            Descricao.setHorizontalAlignment(Element.ALIGN_CENTER);
            Descricao.setVerticalAlignment(Element.ALIGN_MIDDLE);

            // Adicionando célula com os dados do banco
            PdfPCell nome = new PdfPCell(new Phrase(resultado.getString("nome"), fontTable));
            nome.setPadding(5);
            nome.setBorder(nome.NO_BORDER);
            nome.setHorizontalAlignment(Element.ALIGN_CENTER);
            nome.setVerticalAlignment(Element.ALIGN_MIDDLE);

            // Adicionando célula com os dados do banco
            PdfPCell local = new PdfPCell(new Phrase(resultado.getString("localização.nome"), fontTable));
            local.setPadding(5);
            local.setBorder(local.NO_BORDER);
            local.setHorizontalAlignment(Element.ALIGN_CENTER);
            local.setVerticalAlignment(Element.ALIGN_MIDDLE);

            // Adicionando célula com os dados do banco
            PdfPCell categoria = new PdfPCell(new Phrase(resultado.getString("categorias.nome"), fontTable));
            categoria.setPadding(5);
            categoria.setBorder(categoria.NO_BORDER);
            categoria.setHorizontalAlignment(Element.ALIGN_CENTER);
            categoria.setVerticalAlignment(Element.ALIGN_MIDDLE);
        }
        return tableCabecalho;
    }
}
