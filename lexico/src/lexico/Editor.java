package lexico;

import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Equacoes = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        ResultadosTab = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelaAnalise = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        ErroPane = new javax.swing.JTextPane();
        jLabel2 = new javax.swing.JLabel();
        BarraMenu = new javax.swing.JMenuBar();
        ArquivoMenu = new javax.swing.JMenu();
        AbrirArquivoMenu = new javax.swing.JMenuItem();
        AnalisadoresMenu = new javax.swing.JMenu();
        LexicoMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Equacoes.setColumns(20);
        Equacoes.setRows(5);
        Equacoes.setText("2.2*2+(7/2)-2");
        jScrollPane1.setViewportView(Equacoes);

        jLabel1.setText("Expressões");

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

        BarraMenu.add(AnalisadoresMenu);

        setJMenuBar(BarraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ResultadosTab, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ResultadosTab, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LexicoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LexicoMenuActionPerformed
        //Obtem as equacoes
        //Trocar a textArea no futuro por um JPaneText?
        String str = Equacoes.getText();

        //Cria uma lista de tokens, eles são inicializados com os lexiomas mais informações referentes(ver construtor)
        List<Token> tokens = new ArrayList<>();
        
        //Aqui vamos dividir os elementos em tokens nao classificados
        //String temporaria para auxiliar no laço, ela vai basicamente servir para armazenar numeros, maiores informações no laço
        String tmp="";
        //auxiliares para armazenar as linhas e colunas
        int i=1;
        int ini=1;
        int linha=1;
        
        //Percorre toda string original caractere a caractere
        for(char ch: str.toCharArray())
        {
          //Comparaçao simples de um caractere nao justifica uso de regex
          if( (ch >= '0' && ch <= '9') || ch == '.' )
          {
              //Aqui isolaremos os numeros, o ponto é adicionado por causa dos reais
              tmp += ch;
              
          }else{
              //Verificamos se existe um conteudo em tmp, se existir adiciona aos tokens nao classificados
              //Sinal que a interaçao anterior pegou um numero
              if(tmp != "")
                  tokens.add(new Token(tmp,linha,ini,i-1));
     
              //Limpa o tmp
              tmp = "";
              ini = i;
              //ignoramos os espacos em branco
              if(ch == ' '){
                  continue;
              }
              //Pegamos a quebra de linha, sendo que esta define o final de uma equacao
              else if(ch == '\n'){
                  linha++;
                  ini=1;
                  i=1;
              }
              else{
                //Adicionamos o simbolo, problema é que é necessário converter char para string porque o java nao faz automaticamente
                tokens.add(new Token(String.valueOf(ch),linha,ini,i));
                ini = i+1;
              }
          }
          i++;
        }
        //Repetimos esse trecho pois o ultimo valor pode ser um numero nao inserido na tabela
        if(tmp != "")
            tokens.add(new Token(tmp,linha,ini,i-1));
        
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
        for(Token token:tokens){
            token.classificaToken();
            if(token.erro == null)
                modelo.addRow(new String[]{token.lexema,token.tipo.name(),token.linha + "",token.coluna_ini + "",token.coluna_fim+"","------"});
            else{
                modelo.addRow(new String[]{token.lexema,"---------",token.linha + "",token.coluna_ini + "",token.coluna_fim+"",token.erro.name()});
                //String de erro que irá preencher a area de erro
                //O resultado é algo como:
                //"ERRO: 4:2 "x" => Não pertence ao alfabeto
                erro += "ERRO: "+token.linha+":"+token.coluna_ini+" \""+token.lexema+"\" => "+token.erro.name()+"\n";
            }
        }
        //Joga tudo pra tabela
        TabelaAnalise.setModel(modelo);
        
        //Se ocorreu um erro
        if(erro != "")
        {
            //Deixa o texto vermelho e insere o texto de erro no jpane
            MutableAttributeSet attributes = new SimpleAttributeSet();
            StyleConstants.setForeground(attributes, Color.red);
            ErroPane.setCharacterAttributes(attributes, false);
            ErroPane.setText(erro);
            ResultadosTab.setSelectedIndex(1);
        }else{
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
                Equacoes.setText(conteudo);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_AbrirArquivoMenuActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Editor().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AbrirArquivoMenu;
    private javax.swing.JMenu AnalisadoresMenu;
    private javax.swing.JMenu ArquivoMenu;
    private javax.swing.JMenuBar BarraMenu;
    private javax.swing.JTextArea Equacoes;
    private javax.swing.JTextPane ErroPane;
    private javax.swing.JMenuItem LexicoMenu;
    private javax.swing.JTabbedPane ResultadosTab;
    private javax.swing.JTable TabelaAnalise;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
