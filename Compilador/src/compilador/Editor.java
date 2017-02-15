package compilador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gabrielozaki
 */
public class Editor extends javax.swing.JFrame {

    /**
     * Creates new form Editor
     */
    public Editor() {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    //Cria tabela de estilos para o editor
    private StyledDocument syntaxHigh = (StyledDocument) new DefaultStyledDocument();
    //Cria uma instancia do analisador sintatico, que ira passar os dados para o lexico
    AnalisadorSintatico as = new AnalisadorSintatico();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        ResultadosTab = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelaAnalise = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        ErroPane = new javax.swing.JTextPane();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        EditorTexto = new javax.swing.JTextPane(syntaxHigh);
        BarraMenu = new javax.swing.JMenuBar();
        ArquivoMenu = new javax.swing.JMenu();
        AbrirArquivoMenu = new javax.swing.JMenuItem();
        AnalisadoresMenu = new javax.swing.JMenu();
        LexicoMenu = new javax.swing.JMenuItem();
        SintaticoMenu = new javax.swing.JMenuItem();
        AjudaMenu = new javax.swing.JMenu();
        Definições = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Editor");

        TabelaAnalise.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lexoma", "Token", "Linha", "Coluna Inicial", "Coluna Final", "Erro"
            }
        ));
        jScrollPane2.setViewportView(TabelaAnalise);

        ResultadosTab.addTab("Tabela lexica", jScrollPane2);

        ErroPane.setEditable(false);
        jScrollPane4.setViewportView(ErroPane);

        ResultadosTab.addTab("Erro", jScrollPane4);

        jLabel2.setText("Saída");

        EditorTexto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                EditorTextoKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(EditorTexto);

        ArquivoMenu.setText("Arquivo");

        AbrirArquivoMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        AbrirArquivoMenu.setText("Abrir Arquivo");
        AbrirArquivoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirArquivoMenuActionPerformed(evt);
            }
        });
        ArquivoMenu.add(AbrirArquivoMenu);

        BarraMenu.add(ArquivoMenu);

        AnalisadoresMenu.setText("Analisadores");

        LexicoMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        LexicoMenu.setText("Analise Lexica");
        LexicoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LexicoMenuActionPerformed(evt);
            }
        });
        AnalisadoresMenu.add(LexicoMenu);

        SintaticoMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        SintaticoMenu.setText("Analise Sintatica");
        SintaticoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SintaticoMenuActionPerformed(evt);
            }
        });
        AnalisadoresMenu.add(SintaticoMenu);

        BarraMenu.add(AnalisadoresMenu);

        AjudaMenu.setText("Ajuda");

        Definições.setText("Sobre");
        Definições.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DefiniçõesActionPerformed(evt);
            }
        });
        AjudaMenu.add(Definições);

        BarraMenu.add(AjudaMenu);

        setJMenuBar(BarraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ResultadosTab, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ResultadosTab, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LexicoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LexicoMenuActionPerformed
        //Obtem as equacoes
        //Trocar a textArea no futuro por um JPaneText?
        String str = EditorTexto.getText() + "\n";
        //Pra limpar
        this.as = new AnalisadorSintatico();
        //Recebe uma lista de tokens 
        this.as.populaLexico(str);
        List<Token> tokens = this.as.apenasLexico();

        //Construimos novamente a tabela de simbolos, para poder preenche-la no for abaixo
        DefaultTableModel modelo = new javax.swing.table.DefaultTableModel();
        modelo.addColumn("Lexema");
        modelo.addColumn("Token");
        modelo.addColumn("Linha");
        modelo.addColumn("Coluna Inicial");
        modelo.addColumn("Coluna Final");
        modelo.addColumn("Erro");

        //Insere os resultados na tabela
        String erro = "";
        //Define o estilo vermelho
        //Define o estilo vermelho
        MutableAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setForeground(attributes, Color.red);
        StyleConstants.setBold(attributes, true);

        for (Token token : tokens) {
            if (token.erro == null) {
                modelo.addRow(new String[]{token.lexema, token.tipo.name(), token.linha + "", token.coluna_ini + "", token.coluna_fim + "", "------"});
            } else {
                modelo.addRow(new String[]{token.lexema, "---------", token.linha + "", token.coluna_ini + "", token.coluna_fim + "", token.erro.name()});

                //String de erro que irá preencher a area de erro
                //O resultado é algo como:
                //"ERRO: 4:2 "x" => Não pertence ao alfabeto
                erro += "ERRO: " + token.linha + ":" + token.coluna_ini + " \"" + token.lexema + "\" => " + token.erro.name() + "\n";
                Pattern p;
                //O regex de { requer o uso de \\, ogo tratamos como um caso especial
                if (token.erro == Token.Erro.Comentario_Nao_Fechado) {
                    p = Pattern.compile("\\{");
                } else {
                    p = Pattern.compile(token.lexema);
                }

                //Pesquisa pelo erro
                Matcher m = p.matcher(str);
                //Para cada ocorrencia do erro, ele vai deixar vermelho e em negrito
                while (m.find()) {
                    syntaxHigh.setCharacterAttributes(m.start(), token.lexema.length(), attributes, true);
                }
            }
        }
        //Joga tudo pra tabela
        TabelaAnalise.setModel(modelo);
        //Se ocorreu um erro
        if (erro != "") {
            //Deixa o texto vermelho e insere o texto de erro no jpane
            ErroPane.setCharacterAttributes(attributes, false);
            ErroPane.setText(erro);
            ResultadosTab.setSelectedIndex(1);

            //Pra poder alterar a cor de uma linha em um JTable, temos que criar um novo renderer
            //Essa implementação cria como se fosse uma clase filha do Renderer default
            //Referencias:
            //http://stackoverflow.com/questions/24848314/change-background-color-of-jtable-row-based-on-column-value
            //
            TabelaAnalise.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                //Aqui ele vai dar um tratamento em cada componente
                public Component getTableCellRendererComponent(JTable table,
                        Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                    //pega os atributos da classe pai
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                    //Pega o valor da coluna lexema
                    String status = (String) table.getModel().getValueAt(row, 1);
                    //Se for igual a nossa STRING de quando ocorre erro
                    if ("---------".equals(status)) {
                        //Pinta de vermelho
                        setBackground(Color.RED);
                        setForeground(Color.WHITE);
                    } else {
                        //Se não segue os valores default
                        setBackground(table.getBackground());
                        setForeground(table.getForeground());
                    }
                    return this;
                }
            });

        } else {
            //Se nao houve erros, limpa jpane e coloca a aba na tabela
            ErroPane.setText("");
            ResultadosTab.setSelectedIndex(0);
        }


    }//GEN-LAST:event_LexicoMenuActionPerformed

    private void AbrirArquivoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirArquivoMenuActionPerformed
        //Foi escolhido o CTRL+O como atalho porque o CTRL+A selecionava todo texto da TextArea
        // Explorador de arquivos do Java, exemplo veio do site da oracle
        JFileChooser fc = new JFileChooser();
        //Faz ele abrir inicialmente na pasta do projeto
        fc.setCurrentDirectory(new java.io.File("."));
        //Abre o explorador
        int returnVal = fc.showOpenDialog(Editor.this);
        //Se foi clicado no abrir arquivo
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //Pega o arquivo selecionado
            File file = fc.getSelectedFile();
            try {
                //Joga o conteudo dele numa string
                //O delimitador Z evita dolocar uma nova linha no final do arquivo
                //Referencia: http://stackoverflow.com/questions/2707870/whats-the-difference-between-z-and-z-in-a-regular-expression-and-when-and-how
                String conteudo = new Scanner(file).useDelimiter("\\Z").next();
                //Joga o conteudo na textArea das equacoes
                EditorTexto.setText(conteudo);
                this.coloreCodigo();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        DefaultTableModel modelo = new javax.swing.table.DefaultTableModel();
        modelo.addColumn("Lexema");
        modelo.addColumn("Token");
        modelo.addColumn("Linha");
        modelo.addColumn("Coluna Inicial");
        modelo.addColumn("Coluna Final");
        modelo.addColumn("Erro");

        TabelaAnalise.setModel(modelo);
        this.as = new AnalisadorSintatico();
    }//GEN-LAST:event_AbrirArquivoMenuActionPerformed

    private void EditorTextoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EditorTextoKeyReleased
        //Evita colorir em teclas especiais, como f5 
        if (!evt.isActionKey()) {
            this.coloreCodigo();
        }
    }//GEN-LAST:event_EditorTextoKeyReleased

    private void DefiniçõesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DefiniçõesActionPerformed
        // TODO add your handling code here:
        Sobre s = new Sobre();
        s.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_DefiniçõesActionPerformed

    private void SintaticoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SintaticoMenuActionPerformed
        // TODO add your handling code here:
        as.executaAnalise();
        String s = ErroPane.getText();
        if (as.erro.size() >= 1) {
            for (String e : as.erro) {
                s = s + "\n" + e;
            }
            ResultadosTab.setSelectedIndex(1);
        }

    }//GEN-LAST:event_SintaticoMenuActionPerformed

    private void coloreCodigo() {
        //Pega todo texto
        String str = EditorTexto.getText();

        //Lista de tudo que queremos colorir
        String[] comandos = {"\\bprogram\\b", "\\bvar\\b", "\\bprocedure\\b", "\\binteger\\b", "\\bint\\b", "\\breal\\b", "\\bboolean\\b",
            "\\btrue\\b", "\\bfalse\\b", "\\bread\\b", "\\bwrite\\b", "\\bbegin\\b", "\\bend\\b", "\\bif\\b", "\\bthen\\b", "\\belse\\b", "\\bwhile\\b", "\\bdo\\b",
            "\\band\\b", "\\bor\\b", "\\bdiv\\b", "\\bnot\\b"};

        MutableAttributeSet attributes = new SimpleAttributeSet();
        //Pinta de preto e sem realce
        StyleConstants.setForeground(attributes, Color.black);
        StyleConstants.setBold(attributes, false);
        syntaxHigh.setCharacterAttributes(0, str.length(), attributes, true);
        //Pinta de azul e deixa negrito
        StyleConstants.setForeground(attributes, Color.blue);
        StyleConstants.setBold(attributes, true);
        for (String c : comandos) {
            //Localiza o padrao para realcar
            Pattern p = Pattern.compile(c);
            Matcher m = p.matcher(str);
            //Para cada ocorrencia do erro, ele vai deixar vermelho e em negrito
            while (m.find()) {
                syntaxHigh.setCharacterAttributes(m.start(), c.length() - 4, attributes, true);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Editor().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AbrirArquivoMenu;
    private javax.swing.JMenu AjudaMenu;
    private javax.swing.JMenu AnalisadoresMenu;
    private javax.swing.JMenu ArquivoMenu;
    private javax.swing.JMenuBar BarraMenu;
    private javax.swing.JMenuItem Definições;
    private javax.swing.JTextPane EditorTexto;
    private javax.swing.JTextPane ErroPane;
    private javax.swing.JMenuItem LexicoMenu;
    private javax.swing.JTabbedPane ResultadosTab;
    private javax.swing.JMenuItem SintaticoMenu;
    private javax.swing.JTable TabelaAnalise;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
