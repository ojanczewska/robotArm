package org.jdesktop.j3d.examples.alternate_appearance;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import org.jdesktop.j3d.examples.CollisionDetector;

import javax.media.j3d.*;
import javax.sound.sampled.AudioPermission;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;



public class RobotArm extends JFrame implements KeyListener, ActionListener {


    private TextField angleTextField = new TextField("Angle",6);
    private Button poczatek = new Button("Polozenie poczatkowe");
    private Button nagrywanie = new Button("Nagrywanie");
    private Button odtwórz = new Button("Odtwórz");
    private Button stop = new Button("Koniec nagrywania");
    private Button muzyka = new Button("Dźwiek");

    // SimpleBehavior  RobotBehavior;
    Panel p = new Panel(new GridLayout(17,3,3,3));
    Color3f ultramaryna = new Color3f(0.227f, 0.909f, 0.949f); //
    Font font = new Font("SansSerif", Font.PLAIN,30);
    Appearance ap_floor = new Appearance();
    Appearance ap_Box = new Appearance();
    Appearance ap = new Appearance();
    Appearance ap_testObj = new Appearance();
    Color3f TestCol = new Color3f(0.941f, 0.941f, 0.937f);
    Color3f black = new Color3f(1.0f,1.0f,1.0f);
    SimpleUniverse Uni;
    Transform3D przesuniecie;

    public float kat1 = 0.0f;
    public float kat2 = 0.0f;
    public float kat3 = 0.0f;
    public float wys  = 0.0f;
    public float x = 0.0f;
    public int wdobrastrone;
    private TransformGroup objRotate;
    private    TransformGroup trans_floor,CylinderR, trans_waist, trans_body,trans_mod,trans_arm, trans_mod2,trans_arm2,TestObj;
    private  Transform3D setFloor, setPilka, setPodstwa, setwaist,setBody,setMod, setArm,   setMod2,setArm2;
    private Transform3D obrot1 = new Transform3D();
    private Transform3D obrot2 = new Transform3D();
    private Transform3D obrot3 = new Transform3D();
    private Transform3D obrot4 = new Transform3D();
    private Transform3D obrot5 = new Transform3D();
    private Transform3D obrot6 = new Transform3D();
    private Transform3D setMody = new Transform3D();
    private Transform3D setMody2 = new Transform3D();
    private Transform3D setF1 = new Transform3D();
    private Transform3D setF2 = new Transform3D();
    private Transform3D setF3 = new Transform3D();
    private Transform3D setFloor1 = new Transform3D();
    //private  Transform3D przesuniecie;
    private boolean trzymasz=false;
    private  TransformGroup transformacja2,  transformacja;
    private BranchGroup pileczka;
    /** Zmienna opisująca aktualne położenie naszej sfery. */
    public float pozX = 0.0f, pozY = 2.35f, pozZ = 0.5f;
   // (0.0f,2.35f,.5f)
    /** Zmienna opisująca położenie naszej sfery przed zapisem trajektorii. */
    public float pPozX = 0.0f, pPozY = 0.0f, pPozZ = 0.0f;
    public boolean zapis = false;
    public boolean zapisano = false;
    public int tabRuchy[]= new int[1000];
    public int iloscRuchy = 0;
    public int nrRuchu=0;
    public float ruch = 0;
    /** Zmienna opisująca odpowiedni parametr ramion robota, na początku zapisywania trajektorii. */
    public float poczKat1 = 0.0f;
    public float poczKat2 = 0.0f;
    public float poczKat3 = 0.0f;
    private boolean graj=false;

    private float ramiex=0;
    private  float ramiey=0;
    private float ramiez=0;





    public void obrót_prawo () {
        //System.err.println("x:" +pozX+"y:"+pozY+"z:"+pozZ);

        kat1 += 0.01;
        obrot1.rotY(kat1);
        setMody.setTranslation(new Vector3f(0.f,.5f,0.0f));
        obrot1.mul(setMody);
        trans_waist.setTransform(obrot1);
    }
    public void obrót_lewo (){
       // System.err.println("x:" +pozX+"y:"+pozY+"z:"+pozZ);

        kat1 -= 0.01;
        obrot1.rotY(kat1);
        setMody.setTranslation(new Vector3f(0.f,.5f,0.0f));
        obrot1.mul(setMody);
        trans_waist.setTransform(obrot1);


    }
    public  void prz_gora (){
       // System.err.println("x:" +pozX+"y:"+pozY+"z:"+pozZ);

        //if(kat2>=-3.0) {

            kat2 -= 0.01;
            obrot3.rotX(kat2);
            obrot4.rotZ(Math.PI / 2);
            setMody.setTranslation(new Vector3f(0.f, .4f, 0.0f));
            obrot3.mul(obrot4);
            obrot3.mul(setMody);
            trans_mod.setTransform(obrot3);


    }
    public  void prz_dol (){
        //System.err.println("x:" +pozX+"y:"+pozY+"z:"+pozZ);
       // if(kat2<=.3) {

            kat2 += 0.01;
            obrot3.rotX(kat2);
            obrot4.rotZ(Math.PI / 2);
            setMody.setTranslation(new Vector3f(0.f, .4f, 0.0f));
            obrot3.mul(obrot4);
            obrot3.mul(setMody);
            trans_mod.setTransform(obrot3);


    }


    public  void prz_przod (){
        //System.err.println("x:" +pozX+"y:"+pozY+"z:"+pozZ);

        //if(kat3>=-3.0) {

            kat3 -= 0.01;
            obrot5.rotY(kat3);
            obrot6.rotX(Math.PI / 2);
            setMody2.setTranslation(new Vector3f(0.f, 0.15f, -0.45f));
            obrot6.mul(setMody2);
            obrot6.mul(obrot5);
            trans_mod2.setTransform(obrot6);



    }
    public  void prz_tyl (){

       // System.err.println("x:" +pozX+"y:"+pozY+"z:"+pozZ);
        //if(kat3<=3.0) {

            kat3 += 0.01;
            obrot5.rotY(kat3);
            obrot6.rotX(Math.PI / 2);
            setMody2.setTranslation(new Vector3f(0.f, 0.15f, -0.45f));
            obrot6.mul(setMody2);
            obrot6.mul(obrot5);
            trans_mod2.setTransform(obrot6);

    }
    public void lapana() {

        if(!trzymasz){
            if(CollisionDetector.inCollision==true){

                System.err.println("lapiemy!!");
                trzymasz = true;
                objRotate.removeChild(pileczka);
                Transform3D tp = new Transform3D();
                Transform3D tp2 = new Transform3D();
                tp2.setTranslation(new Vector3f(0.2f, .0f, 0.f));
                tp.setScale(6.6d);
                tp.mul(tp2);
                TestObj.setTransform(tp);

                transformacja2.addChild(pileczka);
            }
        }
        else {
            System.err.println("puszczamy!!");
            trzymasz = false;
            transformacja2.removeChild(pileczka);
            Transform3D tp3 = new Transform3D();
            Transform3D tp4 = new Transform3D();
            Transform3D tp5 = new Transform3D();
            Transform3D tp6 = new Transform3D();
            Transform3D tp7 = new Transform3D();
            tp3.setTranslation(new Vector3f(0.f, 2.f, 0.f));
            tp4.setScale(1.15d);
            tp4.mul(tp3);
            TestObj.setTransform(tp4);
            objRotate.addChild(pileczka);

        }

    }


    public void ust_pocz(){
        kat1 = 0.0f;
        kat2 = 0.0f;
        kat3 = 0.0f;

        obrot1.rotY(kat1);
        setMody.setTranslation(new Vector3f(0.f,.5f,0.0f));
        obrot1.mul(setMody);
        trans_waist.setTransform(obrot1);


        obrot3.rotX(kat2);
        obrot4.rotZ(Math.PI / 2);
        setMody.setTranslation(new Vector3f(0.f,0.4f,0.0f));
        obrot3.mul(obrot4);
        obrot3.mul(setMody);
        trans_mod.setTransform(obrot3);


        obrot5.rotY(kat3);
        obrot6.rotX(Math.PI / 2);
        setMody2.setTranslation(new Vector3f(0.0f,0.15f,-0.45f));
        obrot6.mul(obrot5);
        obrot6.mul(setMody2);
        trans_mod2.setTransform(obrot6);

        setFloor1.setTranslation(new Vector3f(0.7f,2.0f,-0.5f));
        setF1.rotX(0);
        setF2.rotY(0);
        setF3.rotZ(0);
        setFloor1.mul(setF1);
        setFloor1.mul(setF2);
        setFloor1.mul(setF3);
        trans_floor.setTransform(setFloor1);
        //Uni.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie);

    }

    /*
    public void PlaySound(){
        if(graj){
            try
            {
                AudioPlayer.player.start(new FileInputStream("gfx/bum.wav"));
            } catch (FileNotFoundException fnfe){System.out.println("cant read file");};
PlaySound();
        }

     */

   // }


    @Override
    public void keyPressed(KeyEvent e) {


        switch (e.getKeyCode()) {
            case KeyEvent.VK_D:
                if(CollisionDetector.inCollision && wdobrastrone !=1 ||CollisionDetector.inCollision == false|| trzymasz==true){
                if(!CollisionDetector.inCollision)
                    wdobrastrone=1;

                if(zapis==true){
                    tabRuchy[iloscRuchy] = 1;
                    iloscRuchy++;
                }
                    obrót_prawo();
                }
                break;
            case KeyEvent.VK_A:
                if(CollisionDetector.inCollision && wdobrastrone !=2 ||CollisionDetector.inCollision == false|| trzymasz==true){
                    if(!CollisionDetector.inCollision)
                        wdobrastrone=2;

                    if(zapis==true){
                        tabRuchy[iloscRuchy] = 2;
                        iloscRuchy++;
                    }
                    obrót_lewo();
                }
                break;
            case KeyEvent.VK_W:
                if(CollisionDetector.inCollision && wdobrastrone !=3 ||CollisionDetector.inCollision == false|| trzymasz==true){
                    if(!CollisionDetector.inCollision)
                        wdobrastrone=3;

                    if(zapis==true){
                        tabRuchy[iloscRuchy] = 3;
                        iloscRuchy++;
                    }
                    prz_gora();
                }
                break;
            case KeyEvent.VK_S:
                if(CollisionDetector.inCollision && wdobrastrone !=4 ||CollisionDetector.inCollision == false|| trzymasz==true){
                    if(!CollisionDetector.inCollision)
                        wdobrastrone=4;

                    if(zapis==true){
                        tabRuchy[iloscRuchy] = 4;
                        iloscRuchy++;
                    }
                    prz_dol();
                }
                break;
            case KeyEvent.VK_Z:
                if(CollisionDetector.inCollision && wdobrastrone !=5 ||CollisionDetector.inCollision == false|| trzymasz==true){
                    if(!CollisionDetector.inCollision)
                        wdobrastrone=5;

                    if(zapis==true){
                        tabRuchy[iloscRuchy] = 5;
                        iloscRuchy++;
                    }
                    prz_przod();
                }
                break;
            case KeyEvent.VK_X:
                if(CollisionDetector.inCollision && wdobrastrone !=6 ||CollisionDetector.inCollision == false|| trzymasz==true){
                    if(!CollisionDetector.inCollision)
                        wdobrastrone=6;

                    if(zapis==true){
                        tabRuchy[iloscRuchy] = 6;
                        iloscRuchy++;
                    }
                    prz_tyl();
                }
                break;
            case KeyEvent.VK_Q:
                //ZLE DANE DO OPUCSZANIA PILKI
                System.err.println("proba podniesienie pilki");
                //if(CollisionDetector.inCollision)
                if(zapis==true){
                    tabRuchy[iloscRuchy] = 7;
                    iloscRuchy++;
                }
                lapana ();
                //else  System.err.println("nie mozna podniesc pilki");
                break;


        }
    }

    public void keyReleased (KeyEvent e){

    }

    public  void keyTyped( KeyEvent e){

    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource()==poczatek){
            ust_pocz();
        }
        if(e.getSource()==muzyka){
            if(graj==false)
                graj=true;
            else graj=false;
        }
        if(e.getSource() == nagrywanie){

            System.err.println("NAGRYWAMY");
            tabRuchy = new int[1000];
            zapis=true;
            poczKat1 = kat1;
            poczKat2 = kat2;
            poczKat3 = kat3;

            //plus pozycja pilki hehe


        }
        if(e.getSource() == stop){

            zapis=false;
            zapisano=true;
            iloscRuchy=0;

        }
        if(e.getSource() == odtwórz) {

            //if (zapisano) {

                System.err.println("OTWARZANIE");


                kat1 = poczKat1;
                kat2 = poczKat2;
                kat3 = poczKat3;

                obrot1.rotY(kat1);
                setMody.setTranslation(new Vector3f(0.f, .5f, 0.0f));
                obrot1.mul(setMody);
                trans_waist.setTransform(obrot1);

                obrot3.rotX(kat2);
                obrot4.rotZ(Math.PI / 2);
                setMody.setTranslation(new Vector3f(0.f, .4f, 0.0f));
                obrot3.mul(obrot4);
                obrot3.mul(setMody);
                trans_mod.setTransform(obrot3);

                obrot5.rotY(kat3);
                obrot6.rotX(Math.PI / 2);
                setMody2.setTranslation(new Vector3f(0.f, 0.15f, -0.45f));
                obrot6.mul(setMody2);
                obrot6.mul(obrot5);
                trans_mod2.setTransform(obrot6);

                while (tabRuchy[nrRuchu] != 0) {
                    if (tabRuchy[nrRuchu] == 1)
                    {
                        while(ruch < 1f)
                        {
                            ruch += 0.0000001f;
                        }
                        ruch = 0.0f;
                        obrót_prawo();
                    }
                    else
                        if (tabRuchy[nrRuchu] == 2)
                    {
                        while(ruch < 1f)
                        {

                            ruch += 0.0000001f;
                        }
                        ruch = 0.0f;
                        obrót_lewo();
                    }
                    else
                        if (tabRuchy[nrRuchu] == 3)
                    {
                        while(ruch < 1f)
                        {

                            ruch += 0.0000001f;
                        }
                        ruch = 0.0f;
                        prz_gora();
                    }
                    else
                        if (tabRuchy[nrRuchu] == 4)
                    {
                        while(ruch < 1f)
                        {

                            ruch += 0.0000001f;
                        }
                        ruch = 0.0f;
                        prz_dol();
                    }
                    else
                        if (tabRuchy[nrRuchu] == 5)
                    {
                        while(ruch < 1f)
                        {

                            ruch += 0.0000001f;
                        }
                        ruch = 0.0f;
                        prz_przod();
                    }

                    else
                        if (tabRuchy[nrRuchu] == 6)
                    {
                        while(ruch < 1f)
                        {

                            ruch += 0.0000001f;
                        }
                        ruch = 0.0f;
                        prz_tyl();
                    }
                    else
                        if (tabRuchy[nrRuchu] == 7)
                    {
                        while(ruch < 1f)
                        {

                            ruch += 0.0000001f;
                        }
                        ruch = 0.0f;
                        lapana();
                    }


                    nrRuchu++;
                }

                nrRuchu = 0;


            }
       // }
    }


     RobotArm(){


        super("Grafika 3D");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();


        Canvas3D canvas3D = new Canvas3D(config);
         canvas3D.setPreferredSize(new Dimension(800,600));
         //canvas3D.setBackground(Color.blue);

         add(canvas3D);
         pack();
         setVisible(true);
         addKeyListener(this);



        BranchGroup scena = utworzScene();
        scena.compile();


        //Transform3D
         przesuniecie = new Transform3D();
        przesuniecie.set(new Vector3f(0.0f,2.5f,10f));


        SimpleUniverse Uni= new SimpleUniverse(canvas3D);
        Uni.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie);
        Uni.addBranchGraph(scena);



    }

    BranchGroup utworzScene() {


        p.setBackground(Color.blue);
        p.add(poczatek,BorderLayout.PAGE_END);
        poczatek.addActionListener(this);
        poczatek.addKeyListener(this);
        p.add(nagrywanie,BorderLayout.PAGE_START);
        nagrywanie.addActionListener(this);
        nagrywanie.addKeyListener(this);
        p.add(stop,BorderLayout.LINE_START);
        stop.addActionListener(this);
        stop.addKeyListener(this);
        p.add(odtwórz,BorderLayout.LINE_START);
        odtwórz.addActionListener(this);
        odtwórz.addKeyListener(this);
        p.add(muzyka,BorderLayout.LINE_START);
        muzyka.addActionListener(this);
        muzyka.addKeyListener(this);
        add("West" ,p);


        BranchGroup objRoot = new BranchGroup();
        //getContentPane().setBackground(Color.YELLOW);
        //JFrame.getContentPane().setBackground(Color.YELLOW);








        //sfera
        Appearance wyglad_sfery   = new Appearance();


        TextureLoader loaderx = new TextureLoader("obrazki/clouds.gif",this);
        ImageComponent2D image = loaderx.getImage();
        //image = loaderx.getImage();

        Texture2D niebosklon = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                image.getWidth(), image.getHeight());
        niebosklon.setImage(0, image);
        niebosklon.setBoundaryModeS(Texture.WRAP);
        niebosklon.setBoundaryModeT(Texture.WRAP);

        wyglad_sfery.setTexture(niebosklon);

        Sphere niebo = new Sphere(7f, Sphere.GENERATE_NORMALS_INWARD|Sphere.GENERATE_TEXTURE_COORDS,
                wyglad_sfery);



       //objRoot.addChild(niebo);


        //Podloga
        Box floor= new com.sun.j3d.utils.geometry.Box(3f,0.2f,3f,
                com.sun.j3d.utils.geometry.Box.GENERATE_TEXTURE_COORDS,ap_floor);
        trans_floor= new TransformGroup();

        trans_floor.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans_floor.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        trans_floor.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        trans_floor.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        setFloor = new Transform3D();
        setFloor.setTranslation(new Vector3f(0.7f,2.0f,-0.5f));
        trans_floor.setTransform(setFloor);
        trans_floor.addChild(floor);




        //cylinedr=podstwa robota

        Cylinder podstawa_robota= new Cylinder(0.25f,1.f,Cylinder.GENERATE_TEXTURE_COORDS,ap);
        CylinderR=new TransformGroup();
        CylinderR.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        CylinderR.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        CylinderR.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        CylinderR.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
         setPodstwa= new Transform3D();
        setPodstwa.setTranslation(new Vector3f(0.05f,0.5f,0.0f));
        CylinderR.setTransform(setPodstwa);
        CylinderR.addChild(podstawa_robota);
        trans_floor.addChild(CylinderR);


        //Podstwa2
        Cylinder waist = new Cylinder(0.4f,0.1f,Cylinder.GENERATE_TEXTURE_COORDS,ap);
        trans_waist = new TransformGroup();
        trans_waist.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans_waist.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        trans_waist.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        trans_waist.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        setwaist= new Transform3D();
        setwaist.setTranslation(new Vector3f(0.0f,0.5f,0.0f));
        trans_waist.setTransform(setwaist);
        trans_waist.addChild(waist);
        CylinderR.addChild(trans_waist);


        //Adds handle that is attached to CylinderR
        Box body =  new com.sun.j3d.utils.geometry.Box(0.35f,0.35f,0.35f,
                com.sun.j3d.utils.geometry.Box.GENERATE_TEXTURE_COORDS,ap_Box);
        trans_body=new TransformGroup();
        trans_body.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans_body.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        trans_body.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        trans_body.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        setBody= new Transform3D();
        setBody.setTranslation(new Vector3f(0.0f,0.4f,0.0f));
        trans_body.setTransform(setBody);
        trans_body.addChild(body);
        trans_waist.addChild(trans_body);


        //mod obracanie ramieniem
        Cylinder Mod = new Cylinder(0.25f,0.1f,Cylinder.GENERATE_TEXTURE_COORDS,ap);
        trans_mod = new TransformGroup();
        trans_mod.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans_mod.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        trans_mod.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        trans_mod.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
         setMod= new Transform3D();
        setMod.setTranslation(new Vector3f(-0.4f,0.0f,0.0f));
        Transform3D tmp_Mod= new Transform3D();
        tmp_Mod.rotZ(Math.PI/2);
        setMod.mul(tmp_Mod);
        trans_mod.setTransform(setMod);
        trans_mod.addChild(Mod);
        trans_body.addChild(trans_mod);

        //Adds Arm to robot
        Box Arm = new com.sun.j3d.utils.geometry.Box(0.1f,0.6f,0.1f,
                com.sun.j3d.utils.geometry.Box.GENERATE_TEXTURE_COORDS,ap);
        trans_arm=new TransformGroup();
        trans_arm.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans_arm.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        trans_arm.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        trans_arm.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        setArm= new Transform3D();
        setArm.setTranslation(new Vector3f(0.0f,0.15f,0.5f));
        Transform3D tmp_Arm= new Transform3D();
        tmp_Arm.rotX(Math.PI/2);
        setArm.mul(tmp_Arm);
        trans_arm.setTransform(setArm);
        trans_arm.addChild(Arm);
        trans_mod.addChild(trans_arm);

        //mod2 obracanie ramieniem
        Cylinder Mod2 = new Cylinder(0.15f,0.1f,Cylinder.GENERATE_TEXTURE_COORDS,ap);
        trans_mod2 = new TransformGroup();
        trans_mod2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans_mod2.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        trans_mod2.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        trans_mod2.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        setMod2= new Transform3D();
        setMod2.setTranslation(new Vector3f(0.0f,0.45f,0.15f));

        Transform3D tmp_Mod2= new Transform3D();
        tmp_Mod2.rotX(Math.PI/2);
        setMod2.mul(tmp_Mod2);
        trans_mod2.setTransform(setMod2);
        trans_mod2.addChild(Mod2);
        trans_arm.addChild(trans_mod2);


        //TRZEBA PEWNIE POPRAWIC OORZENIE
        Box Arm2 = new com.sun.j3d.utils.geometry.Box(0.1f,0.5f,0.1f,
                com.sun.j3d.utils.geometry.Box.GENERATE_TEXTURE_COORDS,ap_Box);
        trans_arm2=new TransformGroup();
        trans_arm2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans_arm2.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        trans_arm2.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        trans_arm2.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        setArm2= new Transform3D();
        setArm2.setTranslation(new Vector3f(-0.2f,.15f,-0.3f));
        Transform3D tmp_Arm2= new Transform3D();
        Transform3D tp_Arm2= new Transform3D();
        tmp_Arm2.rotZ(Math.PI/2);
        tp_Arm2.rotX(-Math.PI/3);
        setArm2.mul(tmp_Arm2);
        setArm2.mul(tp_Arm2);
        trans_arm2.setTransform(setArm2);
        trans_arm2.addChild(Arm2);
        trans_mod2.addChild(trans_arm2);


       //ZMIENIC KOLOR
        ObjectFile fLoad = new ObjectFile();
        Scene scenka = null;

        try{

            fLoad.setFlags (ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
            scenka = fLoad.load("obrazki/mod33.obj");

        }
        catch (FileNotFoundException e){
            System.out.printf("FileNotFound");
        }

        Transform3D  polozenie       = new Transform3D();
        Transform3D  obrot_pocz           = new Transform3D();
        Transform3D  skala                = new Transform3D();
        polozenie.set(new Vector3f(0.f, 0.6f, 0.f));
        skala.setScale(0.15d);
        obrot_pocz.rotZ(-Math.PI/2);

        polozenie.mul(obrot_pocz);
        polozenie.mul(skala);
       // TransformGroup
        transformacja = new TransformGroup(polozenie);
        transformacja.setTransform(polozenie);
        transformacja.addChild(scenka.getSceneGroup());

        trans_arm2.addChild(transformacja);

        ObjectFile fLoad2 = new ObjectFile();
        Scene scenka2 = null;

        try{

            fLoad2.setFlags (ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
            scenka2 = fLoad2.load("obrazki/koncowka.obj");

        }
        catch (FileNotFoundException e){
            System.out.printf("FileNotFound");

        }

        Transform3D  polozenie2       = new Transform3D();
        Transform3D  obrot_pocz2          = new Transform3D();
        Transform3D  skala2                = new Transform3D();
        polozenie2.set(new Vector3f(0.0f, 0.7f, .0f));
        skala2.setScale(0.15d);
        obrot_pocz2.rotZ(Math.PI/2);

        polozenie2.mul(obrot_pocz2);
        polozenie2.mul(skala2);
        //TransformGroup
        transformacja2 = new TransformGroup(polozenie);
        transformacja2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformacja2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformacja2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        transformacja2.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        transformacja2.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        transformacja2.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        transformacja2.setTransform(polozenie2);
        transformacja2.addChild(scenka2.getSceneGroup());

        trans_arm2.addChild(transformacja2);

        //pilka
        pileczka = new BranchGroup();
        pileczka.setCapability(pileczka.ALLOW_DETACH);
        pileczka.setCapability(pileczka.ALLOW_CHILDREN_WRITE);
        pileczka.setCapability(pileczka.ALLOW_CHILDREN_READ);

        Sphere pilka = new Sphere(0.15f,Sphere.GENERATE_TEXTURE_COORDS,ap_testObj);
        TestObj = new TransformGroup();

        TestObj.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        TestObj.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        TestObj.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        TestObj.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        setPilka = new Transform3D();
        setPilka.setTranslation(new Vector3f(0.0f,2.35f,.5f));
        TestObj.setTransform(setPilka);
        TestObj.addChild(pilka);
        pileczka.addChild(TestObj);

        //trans_floor.addChild(TestObj);

        //JEDEN ZA DUZO CHYBA
        ap.setMaterial(new Material( black,black,black,black,0.0f));
        ap_Box.setMaterial(new Material(black,black,black,black,0.0f));
        ap_testObj.setMaterial(new Material(black,black,black,black,0.0f));
        ap_floor.setMaterial(new Material(black,black,black,black,0.f));



        //Adds simple texture to ap Appearance
        TextureLoader loader = new TextureLoader("obrazki/blacha2.jpg",null);
        //jedego nie potrzebuje
        TextureLoader loaderBox = new TextureLoader("obrazki/blacha2.jpg",null);
        TextureLoader loadertest_Obj = new TextureLoader("obrazki/pilka.jpg",null);
        TextureLoader loaderfloor = new TextureLoader("obrazki/trawka.gif",null);


       //TEKSTURY
        ImageComponent2D mImage = loader.getImage( );
        Texture2D  tx2 = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, mImage.getWidth(), mImage.getHeight());
        tx2.setImage(0, mImage);
        ap.setTexture(tx2);

        ImageComponent2D BoxImage = loaderBox.getImage( );
        Texture2D  tx3 = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, mImage.getWidth(), mImage.getHeight());
        tx3.setImage(0, BoxImage);
        ap_Box.setTexture(tx3);

        ImageComponent2D test_ObjImage = loadertest_Obj.getImage( );
        Texture2D  tx4 = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, mImage.getWidth(), mImage.getHeight());
        tx4.setImage(0, test_ObjImage);
        ap_testObj.setTexture(tx4);

        ImageComponent2D floorimage = loaderfloor.getImage( );
        Texture2D  txf = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, mImage.getWidth(), mImage.getHeight());
        txf.setImage(0, floorimage);
        ap_floor.setTexture(txf);

        //Lightning

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 10.0f);
        AmbientLight alldirlight = new AmbientLight(ultramaryna);
        alldirlight.setInfluencingBounds(bounds);
        //objRoot.addChild(alldirlight); // adds light in all direction
        Color3f lightCol = new Color3f(1f, 1f, 1f);
        Vector3f lightDir = new Vector3f(0.0f, 0.0f, 0.0f);
        DirectionalLight light = new DirectionalLight (lightCol,lightDir);
        light.setInfluencingBounds(bounds);

        //objRoot.addChild(light);
        //objRoot.addChild(trans_floor);


        Background back = new Background();
        back.setApplicationBounds(bounds);
        // back.setColor(0.17f, 0.65f, 0.92f); // sky colour
        back.setColor(0.2f, 0.0f, .5f); // sky colour
        objRoot.addChild(back);





        //MYSZKA - obsługa

        objRotate = new TransformGroup();
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        objRotate.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        objRotate.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        objRotate.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);


         objRoot.addChild(objRotate);
         objRotate.addChild(trans_floor);
         objRotate.addChild(pileczka);
         //objRotate.addChild(niebo);

        MouseRotate myMouseRotate = new MouseRotate();
        myMouseRotate.setTransformGroup(objRotate);
        myMouseRotate.setSchedulingBounds(new BoundingSphere());
        objRoot.addChild(myMouseRotate);

        //WLACZENIE DETEKCJI KOLOZJI
        CollisionDetector detector = new CollisionDetector(pilka);
        detector.setSchedulingBounds(bounds);

        objRoot.addChild(detector);



        objRoot.compile();
        return objRoot;
    }



    public static void main(String[] args){
         new RobotArm();



    }
}


