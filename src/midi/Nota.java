package midi;

/**
 * Modela um objeto para a representacao de uma nota.
 * 
 * @author Pedro Guimaraes
 */
public class Nota
{
    /**
     * Valor correspondente ao canal no qual a nota ser� executada:
     */
    protected int canal;

    /**
     * N�mero correspondente a uma nota MIDI:
     */
    protected int tecla;

    /**
     * Valor correspondente ao in�cio da nota, em ticks:
     */
    protected long inicio;

    /**
     * Valor correspondente a duracao da nota, em ticks:
     */
    protected long duracao;

    /**
     * Volume da nota, entre minimo(inaudivel) e maximo (audivel e alto):
     */
    protected int intensidade;

    /**
     * Construtor da classe. Recebe os valores de altura, duracao e intensidade da nota.
     * 
     * @param canal
     * @param tecla
     * @param intensidade
     * @param inicio
     * @param duracao
     */
    public Nota(int canal, int tecla, int intensidade, long inicio, long duracao)
    {
        setCanal(canal);
        setTecla(tecla);
        setIntensidade(intensidade);
        setInicio(inicio);
        setDuracao(duracao);
    }
    
    /**
     * Construtor da classe. Inicializa tudo com zero como default.
     * 
     * @param canal
     * @param tecla
     * @param intensidade
     * @param inicio
     * @param duracao
     */
    public Nota()
    {
        setCanal(0);
        setTecla(0);
        setIntensidade(0);
        setInicio(0);
        setDuracao(0);
    }


    public int getCanal()
    {
        return canal;
    }

    public void setCanal(int canal)
    {
        this.canal = canal;
    }

    public int getTecla()
    {
        return tecla;
    }

    public void setTecla(int tecla)
    {
        this.tecla = tecla;
    }

    public long getInicio()
    {
        return inicio;
    }

    public void setInicio(long inicio)
    {
        this.inicio = inicio;
    }

    public int getIntensidade()
    {
        return intensidade;
    }

    public void setIntensidade(int intensidade)
    {
        this.intensidade = intensidade;
    }

    public long getDuracao()
    {
        return duracao;
    }

    public void setDuracao(long duracao)
    {
        this.duracao = duracao;
    }
}