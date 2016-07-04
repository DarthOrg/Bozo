package com.darthorg.bozo.datamodel;

/**
 * Created by Gustavo on 04/07/2016.
 */
public class DataModel {

    private static final String DB_NAME = "db_bozo";
    private static final String TABELA_PARTIDAS = "partidas";
    private static final String TABELA_RODADAS = "rodadas";
    private static final String TABELA_JOGADORES = "jogadores";
    private static final String ID = "_id";
    private static final String NOME = "nome";
    private static final String TIPO_INTEIRO_PK = "INTEGER PRIMARY KEY";
    private static final String TIPO_INTEIRO = "INTEGER";
    private static final String TIPO_TEXT = "TEXT";


    public static String criarTabelaPartidas() {
        String query = " CREATE TABLE " + TABELA_PARTIDAS;
        query += " (";
        query += ID + " " + TIPO_INTEIRO_PK + " AUTOINCREMENT , ";
        query += NOME + " " + TIPO_TEXT + " NOT NULL";
        query += " ); ";

        return query;
    }

    public static String criarTabelaRodadas() {
        String query = " CREATE TABLE " + TABELA_RODADAS;
        query += " (";
        query += ID + " " + TIPO_INTEIRO_PK + " AUTOINCREMENT , ";
        query += "vencedor" + " " + TIPO_TEXT + ", ";
        query += "fk_partida" + " " + TIPO_INTEIRO + "NOT NULL , ";
        query += "FOREIGN KEY (fk_partida) REFERENCES " + TABELA_PARTIDAS + "(_id) ON DELETE CASCADE ";
        query += " ); ";

        return query;
    }

    public static String criarTabelaJogadores() {
        String query = " CREATE TABLE " + TABELA_JOGADORES;
        query += " (";
        query += ID + " " + TIPO_INTEIRO_PK + " AUTOINCREMENT , ";
        query += NOME + " " + TIPO_TEXT + " NOT NULL , ";
        query += "pontuacao" + " " + TIPO_INTEIRO + ", ";
        query += "fk_rodada" + " " + TIPO_INTEIRO + "NOT NULL , ";
        query += "FOREIGN KEY (fk_rodada) REFERENCES " + TABELA_RODADAS + "(_id) ON DELETE CASCADE ";
        query += " ); ";

        return query;
    }

    public static String getDbName() {
        return DB_NAME;
    }

    public static String getTabelaPartidas() {
        return TABELA_PARTIDAS;
    }

    public static String getTabelaRodadas() {
        return TABELA_RODADAS;
    }

    public static String getTabelaJogadores() {
        return TABELA_JOGADORES;
    }

    public static String getID() {
        return ID;
    }
}
