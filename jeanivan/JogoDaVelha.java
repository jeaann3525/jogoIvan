import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JogoDaVelha extends JFrame {
    private JButton[][] botoes;
    private boolean vezX;
    private JLabel statusLabel;
    private int jogadas;

    public JogoDaVelha() {
        setTitle("Jogo da Velha");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        // Inicializar variáveis do jogo
        vezX = true;
        jogadas = 0;
        botoes = new JButton[3][3];

        // Configurar layout
        setLayout(new BorderLayout());

        // Painel do tabuleiro
        JPanel tabuleiroPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        tabuleiroPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Criar botões do tabuleiro
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[i][j] = new JButton("");
                botoes[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                botoes[i][j].setFocusPainted(false);
                botoes[i][j].addActionListener(new BotaoClickListener(i, j));
                tabuleiroPanel.add(botoes[i][j]);
            }
        }

        // Label de status
        statusLabel = new JLabel("Vez do: X", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botão de reiniciar
        JButton reiniciarButton = new JButton("Reiniciar Jogo");
        reiniciarButton.setFont(new Font("Arial", Font.BOLD, 16));
        reiniciarButton.addActionListener(new ReiniciarClickListener());

        // Painel inferior
        JPanel inferiorPanel = new JPanel(new BorderLayout());
        inferiorPanel.add(statusLabel, BorderLayout.CENTER);
        inferiorPanel.add(reiniciarButton, BorderLayout.SOUTH);

        // Adicionar componentes à janela
        add(tabuleiroPanel, BorderLayout.CENTER);
        add(inferiorPanel, BorderLayout.SOUTH);
    }

    private class BotaoClickListener implements ActionListener {
        private int linha, coluna;

        public BotaoClickListener(int linha, int coluna) {
            this.linha = linha;
            this.coluna = coluna;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton botaoClicado = botoes[linha][coluna];

            // Verificar se o botão já foi clicado
            if (!botaoClicado.getText().equals("")) {
                return;
            }

            // Fazer a jogada
            if (vezX) {
                botaoClicado.setText("X");
                botaoClicado.setForeground(Color.BLUE);
                statusLabel.setText("Vez do: O");
            } else {
                botaoClicado.setText("O");
                botaoClicado.setForeground(Color.RED);
                statusLabel.setText("Vez do: X");
            }

            jogadas++;
            vezX = !vezX;

            // Verificar se há vencedor
            verificarVencedor();
        }
    }

    private class ReiniciarClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            reiniciarJogo();
        }
    }

    private void verificarVencedor() {
        String vencedor = null;

        // Verificar linhas
        for (int i = 0; i < 3; i++) {
            if (!botoes[i][0].getText().equals("") &&
                botoes[i][0].getText().equals(botoes[i][1].getText()) &&
                botoes[i][1].getText().equals(botoes[i][2].getText())) {
                vencedor = botoes[i][0].getText();
                destacarVitoria(i, 0, i, 1, i, 2);
            }
        }

        // Verificar colunas
        for (int j = 0; j < 3; j++) {
            if (!botoes[0][j].getText().equals("") &&
                botoes[0][j].getText().equals(botoes[1][j].getText()) &&
                botoes[1][j].getText().equals(botoes[2][j].getText())) {
                vencedor = botoes[0][j].getText();
                destacarVitoria(0, j, 1, j, 2, j);
            }
        }

        // Verificar diagonal principal
        if (!botoes[0][0].getText().equals("") &&
            botoes[0][0].getText().equals(botoes[1][1].getText()) &&
            botoes[1][1].getText().equals(botoes[2][2].getText())) {
            vencedor = botoes[0][0].getText();
            destacarVitoria(0, 0, 1, 1, 2, 2);
        }

        // Verificar diagonal secundária
        if (!botoes[0][2].getText().equals("") &&
            botoes[0][2].getText().equals(botoes[1][1].getText()) &&
            botoes[1][1].getText().equals(botoes[2][0].getText())) {
            vencedor = botoes[0][2].getText();
            destacarVitoria(0, 2, 1, 1, 2, 0);
        }

        if (vencedor != null) {
            statusLabel.setText("Vencedor: " + vencedor + "!");
            desabilitarBotoes();
        } else if (jogadas == 9) {
            statusLabel.setText("Empate!");
        }
    }

    private void destacarVitoria(int l1, int c1, int l2, int c2, int l3, int c3) {
        botoes[l1][c1].setBackground(Color.GREEN);
        botoes[l2][c2].setBackground(Color.GREEN);
        botoes[l3][c3].setBackground(Color.GREEN);
    }

    private void desabilitarBotoes() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[i][j].setEnabled(false);
            }
        }
    }

    private void reiniciarJogo() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[i][j].setText("");
                botoes[i][j].setEnabled(true);
                botoes[i][j].setBackground(null);
            }
        }
        vezX = true;
        jogadas = 0;
        statusLabel.setText("Vez do: X");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JogoDaVelha().setVisible(true);
            }
        });
    }
}