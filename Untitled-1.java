// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class PanelJuego extends JPanel {
   private final int ANCHO = 640;
   private final int ALTO = 480;
   private Jugador jugador;
   private Jugador npc;
   private Trofeo[] trofeos;
   private int contador = 0;
   private final int TOTAL_TROFEOS = 5;
   private boolean victoria = false;

   public PanelJuego() {
      this.jugador = new Jugador(100, 100, 50, 50, 5, Color.BLUE);
      this.npc = new Jugador(400, 150, 50, 50, 0, Color.RED);
      this.trofeos = new Trofeo[5];

      for(int var1 = 0; var1 < 5; ++var1) {
         this.trofeos[var1] = new Trofeo(640, 480);
      }

      this.setFocusable(true);
      this.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent var1) {
            if (!PanelJuego.this.victoria) {
               switch (var1.getKeyCode()) {
                  case 37 -> PanelJuego.this.jugador.mover(-PanelJuego.this.jugador.getVelocidad(), 0);
                  case 38 -> PanelJuego.this.jugador.mover(0, -PanelJuego.this.jugador.getVelocidad());
                  case 39 -> PanelJuego.this.jugador.mover(PanelJuego.this.jugador.getVelocidad(), 0);
                  case 40 -> PanelJuego.this.jugador.mover(0, PanelJuego.this.jugador.getVelocidad());
               }

               PanelJuego.this.verificarColisiones();
               PanelJuego.this.repaint();
            }
         }
      });
   }

   private void verificarColisiones() {
      Rectangle var1 = new Rectangle(this.jugador.getX(), this.jugador.getY(), 50, 50);

      for(int var2 = 0; var2 < this.trofeos.length; ++var2) {
         if (this.trofeos[var2] != null && var1.intersects(this.trofeos[var2].getBounds())) {
            this.trofeos[var2] = null;
            ++this.contador;
         }
      }

      if (this.contador == 5) {
         this.victoria = true;
      }

   }

   protected void paintComponent(Graphics var1) {
      super.paintComponent(var1);
      Graphics2D var2 = (Graphics2D)var1;
      var2.setColor(new Color(0, 120, 0));
      var2.fillRect(0, 0, 640, 480);
      var2.setColor(Color.WHITE);
      var2.drawRect(20, 20, 600, 440);
      var2.drawLine(320, 20, 320, 460);
      var2.drawOval(280, 200, 80, 80);
      this.jugador.dibujar(var2);
      this.npc.dibujar(var2);

      for(Trofeo var6 : this.trofeos) {
         if (var6 != null) {
            var6.dibujar(var2);
         }
      }

      var2.setColor(Color.WHITE);
      var2.drawString("Trofeos: " + this.contador + "/5", 10, 20);
      var2.drawString("X: " + this.jugador.getX() + " Y: " + this.jugador.getY(), 10, 40);
      if (this.victoria) {
         var2.setFont(new Font("Arial", 1, 30));
         var2.setColor(Color.YELLOW);
         var2.drawString("¡GANASTE!", 220, 240);
      }

   }
}