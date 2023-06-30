package jGame.output;

import jGame.Game;
import jGame.core.Size;
import jGame.debug.Stat;
import jGame.exception.BuilderException;
import jGame.output.listener.KeyListenerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Frame implements Output {
    public static class Builder {
        private Size size = null;

        public Builder setSize(Size size) {
            this.size = size;
            return this;
        }

        private int numBufferStrategy = -1;

        public Builder setNumBufferStrategy(int numBufferStrategy) {
            this.numBufferStrategy = numBufferStrategy;
            return this;
        }

        private int defaultCloseOperation = -1;

        public Builder setDefaultCloseOperation(int defaultCloseOperation) {
            this.defaultCloseOperation = defaultCloseOperation;
            return this;
        }

        private String frameTitle = null;

        public Builder setFrameTitle(String title) {
            this.frameTitle = title;
            return this;
        }

        private Image icon = null;

        public Builder setIcon(Image icon) {
            this.icon = icon;
            return this;
        }

        private MouseListener mouseListener = null;
        public Builder setMouseListener(MouseListener mouseListener){
            this.mouseListener = mouseListener;
            return this;
        }

        private KeyListener keyListener;
        public Builder setKeyListener(KeyListener keyListener){
            this.keyListener = keyListener;
            return this;
        }

        public Frame build() {
            if (size != null)
                return new Frame(
                        size,
                        (numBufferStrategy == 0) ? 2 : numBufferStrategy,
                        (defaultCloseOperation == -1) ? JFrame.EXIT_ON_CLOSE : defaultCloseOperation,
                        (frameTitle == null) ? "Game" : frameTitle,
                        icon,
                        mouseListener,
                        keyListener
                );
            else
                throw new BuilderException("There is some missing args.");
        }
    }

    private JFrame jFrame;
    private Canvas canvas;
    private Size size;
    private Game game;

    private Frame(Size size,
                  int numBufferStrategy,
                  int defaultCloseOperation,
                  String frameTitle,
                  Image icon,
                  MouseListener mouseListener,
                  KeyListener keyListener
    ) {
        this.size = size;

        jFrame = new JFrame();
        canvas = new Canvas();

        jFrame.setVisible(false);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(defaultCloseOperation);
        jFrame.setTitle(frameTitle);
        jFrame.setIconImage(icon);

        canvas.setFocusable(false);
        canvas.setSize(new Dimension(size.getIntWidth(), size.getIntHeight()));
        jFrame.getContentPane().add(canvas);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);

        canvas.addMouseListener(mouseListener);
        jFrame.addKeyListener(keyListener);

        canvas.createBufferStrategy(numBufferStrategy);
    }


    public void setGame(Game game) {
        this.game = game;
        Stat.setStatBoolean(Stat.OUTPUT_READY, true);
    }

    public void showFrame() {
        if(game == null){
            throw new NullPointerException("The game object hasn't been set yet.");
        }

        jFrame.setVisible(true);

        new GameLaunching(game).start();
    }


    @Override
    public Graphics getGraphics() {
        return canvas.getBufferStrategy().getDrawGraphics();
    }

    @Override
    public void show() {
        canvas.getBufferStrategy().show();
    }

    @Override
    public Size getSize() {
        return this.size;
    }
}
