// Culture Map of Yang Pu
// December 2018 XKY

import java.applet.*;
import java.awt.*; 
import java.awt.event.*;

public class CultureMap_YP extends Applet implements KeyListener{
	
	Image map; // 背景为杨浦区地图
	Image buil; // 储存建筑图像
	int Tx = 16; int Ty = 20; // 设定旅行者出发坐标
	// 设定23个可访问建筑的坐标
	int[ ] x = new int [ ]{14,16,24,15,16,13,17,15,23,16,15,13,9,18,23,19,17,16,17,15,15,14,14}; 
	int[ ] y = new int [ ]{12,28,23,11,12,13,12,7,24,13,13,11,15,26,23,27,27,27,28,28,29,28,29};
	// 设定4个隐藏建筑的坐标
	int[ ] a = new int [ ]{15,23,24,20};
	int[ ] b = new int [ ]{4,18,19,22};
	String[ ] str = new String [4]; // 设定四则隐藏建筑提示
	boolean[ ] flag = new boolean [27]; // 设定27个建筑flag
	boolean pressed = false; // 避免出发点被覆盖
	long startTime = System.currentTimeMillis(); // 记录开始时间
	 
	public void init(){
	    
		str[0] = "您路过了闸北水厂。";
        str[1] = "您路过了沪江大学旧址。";
        str[2] = "您路过了复兴岛。";
        str[3] = "您路过了隆昌路。";
        
	    addKeyListener( this ); // 为接受键盘输入准备
	    setFocusable( true ); // 获得焦点
	}
    
    // 接受上下左右四键的输入，同时相应改变坐标值
    public void keyPressed( KeyEvent e ){
        switch( e.getKeyCode() ){
            case KeyEvent.VK_UP:
             	 Ty = Ty-1;
    		break;	
            case KeyEvent.VK_DOWN:
             	 Ty = Ty+1;
            break;   		
            case KeyEvent.VK_LEFT:
             	 Tx = Tx-1;
            break;   		
            case KeyEvent.VK_RIGHT:
                 Tx = Tx+1;
            break;
        }
        pressed = true; // 按键后旅行者以绿色出发
        repaint(); // 更新全局
	}
    public void keyReleased(KeyEvent e){} // 按规则必须写
    public void keyTyped(KeyEvent e){} // 按规则必须写

    //  全局绘制
    public void paint( Graphics g ){
            
            // 左侧画出背景地图
            map = getImage( getDocumentBase(), "map.jpg" );
            g.drawImage( map, 0, 0, this );
                       
            // 画出背景方格线
            for( int i = 3; i < 29; i++ ){
                for( int j = 0; j < 32; j++ ){
                    g.drawRect( i*16, j*16, 16, 16 );   
                }
            }
            
            // 固定旅行者初始位置，标黄色
             g.setColor(Color.yellow);
             g.fillRect( 16*16+1, 20*16+1, 16-2, 16-2); 
            
            // 固定建筑位置，访问前标蓝色，访问后标紫红色
            for ( int i = 0; i < 23; i++ ){
                if (flag[i] == false){
                    g.setColor(Color.blue);
                }else{
                    g.setColor(Color.magenta);
                };
                g.fillRect( x[i]*16+1, y[i]*16+1, 16-2, 16-2 );
            }
            
            // 访问后才显示隐藏建筑
            for ( int i = 0; i < 4; i++ ){
                if (flag[i+23] == true){
                    g.setColor(Color.magenta);
                    g.fillRect( a[i]*16+1, b[i]*16+1, 16-2, 16-2 );
                }
            }
            
            // 显示旅行者实时位置
            if ( pressed == true ){
                g.setColor(Color.green);
                g.fillRect( Tx*16+1, Ty*16+1, 16-2, 16-2 ); 
            }
            
            // 统计到访建筑总数
            int n = 0;
            for ( int i = 0; i < 23; i++ ){
                if (flag[i] == true) n++;
            }
            int m = 0;
            for ( int i = 23; i < 27; i++ ){
                if (flag[i] == true) m++;
            }
            
            // 下部页面文字区
            g.setColor(Color.black);
            g.setFont(new Font( "宋体", Font.BOLD, 15 ));            
            g.drawString( "总共有23处历史建筑，您访问了"+n+"处。", 128, 540 );
            
            // 显示总用时
            if (n == 23) {
            	long endTime = System.currentTimeMillis();
            	long Time = (endTime - startTime) / 1000;
            	g.drawString( "您已经访问了杨浦区在此地图上的全部历史建筑！用时"+Time+"秒。", 450, 540 );
            }
            if (m == 4) g.drawString( "您发现了所有隐藏建筑！", 450, 560 );
            
             // 各类提示
            if ( (Tx > 9)&&(Ty >= 30 || Tx >= 26) ) g.drawString( "欢迎来到浦东！", 128, 580 );
            if (Tx <= 6) g.drawString( "欢迎来到虹口！", 128, 580 );
            
            // 文字提示路过隐藏建筑
            for ( int i = 0; i < 4; i++ ){
            	if ( Tx == a[i] && Ty == b[i] ){
            		g.drawString( str[i], 128, 560 );
                    flag[i+23] = true;
            	}
            }
            
            // 右页显示详细建筑介绍
            for ( int i = 0; i < 23; i++ ){
            	if ( Tx == x[i] && Ty == y[i] ){
            		buil = getImage( getDocumentBase(), i+".jpg" );
                    g.drawImage( buil, 512,  0, this );
                    flag[i] = true;
            	}
            }
            
            // System.out.println( Tx+" "+Ty ); // 调试时坐标显示
    }    
}