/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexico;

import java.util.ArrayList;

/**
 *
 * @author Joimar
 */
public class AnalisadorLexico 
{
    
    private final int status_palavra_reservada = 0;
    private final int status_id = 1;
    private final int status_numero = 2;
    private final int status_op_ari = 3;
    private final int status_op_relacional = 4;
    private final int status_op_logico = 5;
    private final int status_comentario = 6;
    private final int status_delimitador = 7;
    private final int status_cadeia = 8;
    private final int status_neutro = 9;
    
    private final String tipo_palavra_reservada = "Palavra Reservada";
    private final String tipo_id = "Identificador";
    private final String tipo_numero = "Numero";
    private final String tipo_op_ari = "Operador Aritmetico";
    private final String tipo_op_relacionais = "Operador Relacional";
    private final String tipo_op_logico = "Operador Logico";
    //private final String tipo_comentario = "Identificador";
    private final String tipo_delimitador = "Delimitador";
    private final String tipo_cadeia = "Cadeia de Caracteres";
    
    private ArrayList<String> codigo;
    
    private int status;
    private int linha = 0;
    private int contador = 0;
    
    private final ArrayList<Token> tokens;
    
    private final  ArrayList<Token> erros;
    private static final char EOF = '\0';
    
    public AnalisadorLexico()
    {
    
        this.codigo = codigo;
        
        this.tokens = new ArrayList<>();
        this.erros = new ArrayList<>();
        this.status = status_neutro;
        
        
    }
    
    public void direcionador(String palavra)
    {
        
        if( (palavra.charAt(contador)>=65 && palavra.charAt(contador)<=90) || (palavra.charAt(contador)>=97 && palavra.charAt(contador)<=122) ) {status = status_id; verificaID(palavra);}
        else if(palavra.charAt(contador)>=48 && palavra.charAt(contador)<=57 ) status = status_numero;
        else if(palavra.charAt(contador)==43 || palavra.charAt(contador)==42 || palavra.charAt(contador)==45 || palavra.charAt(contador)==47){ status = status_op_ari; verificaAri(palavra);}
        else if(palavra.charAt(contador)==61 || palavra.charAt(contador)==60 || palavra.charAt(contador)==62) status = status_op_relacional;
        else if(palavra.charAt(contador)==33 || palavra.charAt(contador)==38 || palavra.charAt(contador)==124) status = status_op_logico;
        
        
    }
    
    public boolean verificaEspaco(char espaco)
    {
    
        if(espaco == 9 || espaco == 10 || espaco == 13 || espaco == 32) return true;
        else return false;
    
    }
    
    public boolean verificaPalavraReservada(String palavra)
    {
    
        if(palavra.equals("const") || palavra.equals("var") || palavra.equals("struct") || palavra.equals("typedef") || 
                palavra.equals("procedure") || palavra.equals("function") || palavra.equals("return") || palavra.equals("start") ||
                palavra.equals("if") || palavra.equals("then") || palavra.equals("else") || palavra.equals("while")
                || palavra.equals("scan") || palavra.equals("print") || palavra.equals("int") || palavra.equals("float") || palavra.equals("bool")
                || palavra.equals("string") || palavra.equals("true") || palavra.equals("false") || palavra.equals("extends"))
        {
        
            return true;
        }
        
        else return false;
    }
    public boolean verificaDigito(char digito)
    {
    
        if(digito>=48 && digito<=57) return true;
        else return false;
    
    }
    public boolean verificaLetra(char letra)
    {
    
        if((letra>=65 && letra<=90) || (letra>=97 && letra<=122)) return true;
        else return false;
    }
    public boolean verificaAri(String deli)
    {
        boolean flagmais = false;
        boolean flagmenos = false;
        String aux = new String();
        Token token;
        for(int i=contador;i<deli.length();i++)
        {
        
              //+
            if(deli.charAt(i)==43)
            {
                if(flagmais==false)
                {
                    aux = Character.toString(deli.charAt(i));
                    if(i<deli.length()-1)// evita estourar o array
                    {
                        if(deli.charAt(i+1)==43)
                        {
                            flagmais=true;
                        }
                        else
                        {
                        
                            token = new Token(linha+1, aux, tipo_op_ari);
                            tokens.add(token);
                        }
                        
                    }
                    else
                    {
                    
                        token = new Token(linha+1,aux,tipo_op_ari);
                        tokens.add(token);
                    }
                    
                }
                else
                {
                
                    aux += deli.charAt(i);
                    token = new Token(linha+1,aux,tipo_op_ari);
                    tokens.add(token);
                    flagmais=false;
                    aux = "";
                    return true;
                }
            }
            
             //-
            else if(deli.charAt(i)==45)
            {
                
                return true;
            }
            //*
            else if(deli.charAt(i)==42){return true;}
             // /
            else if(deli.charAt(i)==47){return true;}
            
            else return false;
        
        }
      
       
        
       
        return false;
        
    }
    public void verificaNumero(String palavra)
    {
    
        String aux = new String();
        
    }
    public void verificaID(String palavra)
    {
        String aux = new String();
        boolean flag = true;
        int i=0;
        for(i=contador;i<palavra.length();i++)
        {
        
            aux += palavra.charAt(i);
            if(verificaLetra(palavra.charAt(i))==false) flag = false;
            else if(verificaDigito(palavra.charAt(i))==false) flag = false;
        }
        contador = i;
        Token token = new Token(linha+1, aux, tipo_id);
        tokens.add(token);
        
        
    }
     
    public void analise(ArrayList<String> texto)
    {
        codigo = texto;
        int teste = texto.get(linha).length();
        
           for(linha = 0; linha < codigo.size(); linha++)
           {
           
                switch(status)
                {
        
                    case status_neutro:
                        direcionador(codigo.get(linha));
                
                    case status_id:
                        //verificaID(codigo.get(linha));
                
                            
                }
               
           
            
        }
        
        
    
       
    
    }
    
    public ArrayList<Token> getTokens()
    {
    
        return tokens;
    
    }
    
    public ArrayList<Token> getErros()
    {
    
        return erros;
    
    }
    
}
