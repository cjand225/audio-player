import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        AudioPlayer model = new AudioPlayer();
        AudioPlayerController controller = new AudioPlayerController(model);
        SwingUtilities.invokeLater(() -> new AudioPlayerView(controller));
    }
}
