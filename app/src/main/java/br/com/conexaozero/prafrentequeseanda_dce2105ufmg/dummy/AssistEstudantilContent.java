package br.com.conexaozero.prafrentequeseanda_dce2105ufmg.dummy;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.conexaozero.prafrentequeseanda_dce2105ufmg.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class AssistEstudantilContent {
    /**
     * An array of sample (dummy) items.
     */
    public static List<PropostaItem> ITEMS = new ArrayList<PropostaItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, PropostaItem> ITEM_MAP = new HashMap<String, PropostaItem>();

    static {
        addItem(new PropostaItem("1", "Ações afirmativas", "Apoio às ações afirmativas"));
        addItem(new PropostaItem("2", "PNAES", "Defender a redução do percentual do Plano Nacional de Assistência Estudantil (PNAES) desviado para fins administrativos"));
        addItem(new PropostaItem("3", "Incentivo a intercambio", "Lutar pelo incentivo financeiro via PNAES para mobilidade acadêmica de estudantes assistidos"));
        addItem(new PropostaItem("4", "Programas assistenciais", "Fomentar debates acerca do custo com os programas assistenciais e os Restaurantes Universitários (RU’s)"));
        addItem(new PropostaItem("5", "Linha intercampi", "Debater a implantação da linha intercampi (Direito / Medicina / Arquitetura / Pampulha)"));
        addItem(new PropostaItem("6", "Carteira estudantil", "Implantar carteirinha estudantil com múltiplas funções: biblioteca, acesso ao Centro Esportivo Universitário (CEU), crédito bandejão e xerox"));
        addItem(new PropostaItem("7", "Movimento estudantil", "Ampliar diálogo do DCE com coletivos do Movimento Estudantil"));
        addItem(new PropostaItem("8", "Organização da representação", "Estimular a organização dos vários níveis de representação em cada unidade"));
        addItem(new PropostaItem("9", "Defesa de estudantes", "Acompanhar e estruturar defesa dos processos administrativos contra estudantes"));

/*
        addItem(new PropostaItem("1", Resources.getSystem().getString(R.string.prop_item_01_assist_estudantil))); //Ações afirmativas
        addItem(new PropostaItem("2", Resources.getSystem().getString(R.string.prop_item_02_assist_estudantil))); //PNAES
        addItem(new PropostaItem("3", Resources.getSystem().getString(R.string.prop_item_03_assist_estudantil))); //Incetivo a intercambio
        addItem(new PropostaItem("4", Resources.getSystem().getString(R.string.prop_item_04_assist_estudantil))); //Programas assistenciais
        addItem(new PropostaItem("5", Resources.getSystem().getString(R.string.prop_item_05_assist_estudantil))); //Linha intercampi
        addItem(new PropostaItem("6", Resources.getSystem().getString(R.string.prop_item_06_assist_estudantil))); //Carteira estudantil
        addItem(new PropostaItem("7", Resources.getSystem().getString(R.string.prop_item_07_assist_estudantil))); //Movimento estudantil
        addItem(new PropostaItem("8", Resources.getSystem().getString(R.string.prop_item_08_assist_estudantil))); //Organização da representação
        addItem(new PropostaItem("9", Resources.getSystem().getString(R.string.prop_item_09_assist_estudantil))); //Defesa de estudantes
*/

/*
        addItem(new PropostaItem("1", Resources.getSystem().getString(R.string.prop_item_01_esporte_universitario))); //CEU
        addItem(new PropostaItem("2", Resources.getSystem().getString(R.string.prop_item_02_esporte_universitario))); //Prática esportiva
        addItem(new PropostaItem("3", Resources.getSystem().getString(R.string.prop_item_03_esporte_universitario))); //Recursos para as atléticas
        addItem(new PropostaItem("4", Resources.getSystem().getString(R.string.prop_item_04_esporte_universitario))); //Olímpiadas universitária
        addItem(new PropostaItem("5", Resources.getSystem().getString(R.string.prop_item_05_esporte_universitario))); //Diretoria liga das Atléticas
*/
    }

    private static void addItem(PropostaItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class PropostaItem {
        public String id;
        public String content;
        public String meaning;

        public PropostaItem(String id, String content, String meaning) {
            this.id = id;
            this.content = content;
            this.meaning = meaning;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
