package characteristics_extrator;

import java.io.File;
import java.io.FileOutputStream;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ExtractCaracteristicas {
	public static double[] extraiCaracteristicas(File f) {
		
		double[] caracteristicas = new double[10];
		
		double camisaNed = .0;
		double moletomNed = .0;
		double calcaNed = .0;
		double cabeloNed = .0;
		
		double camisaSkinner = .0;
		double paletoSkinner = .0;
		double calcaSkinner = .0;
		double cabeloSkinner = .0;
		double gravataSkinner = .0;

		Image img = new Image(f.toURI().toString());
		PixelReader pr = img.getPixelReader();

		Mat imagemOriginal = Imgcodecs.imread(f.getPath());
        Mat imagemProcessada = imagemOriginal.clone();

		int w = (int)img.getWidth();
		int h = (int)img.getHeight();

		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {

				Color cor = pr.getColor(j,i);

				double r = cor.getRed()*255; 
				double g = cor.getGreen()*255;
				double b = cor.getBlue()*255;

				// Ned Validations
				if(isCamisaNed(r, g, b)) {
					camisaNed++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(isMoletomNed(r, g, b)) {
					moletomNed++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(isCalcaNed(r, g, b)) {
					calcaNed++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(isCabeloNed(r, g, b)) {
					cabeloNed++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}

				// ----------------------------------------------------------

				// Skinner Validations
				if(isCamisaSkinner(r, g, b)) {
					camisaSkinner++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(isPaletoSkinner(r, g, b)) {
					paletoSkinner++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(isCalcaSkinner(r, g, b)) {
					calcaSkinner++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(isCabeloSkinner(r, g, b)) {
					cabeloSkinner++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(isGravataSkinner(r, g, b)) {
					gravataSkinner++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
			}
		}
		
		// Normaliza as características pelo número de pixels totais da imagem para %
//		camisaNed
		caracteristicas[0] = (camisaNed / (w * h)) * 100;
//		moletomNed
		caracteristicas[1] = (moletomNed / (w * h)) * 100;
//		calcaNed
		caracteristicas[2] = (calcaNed / (w * h)) * 100;
//		cabeloNed
		caracteristicas[3] = (cabeloNed / (w * h)) * 100;
		
//		camisaSkinner
		caracteristicas[4] = (camisaSkinner / (w * h)) * 100;
//		paletoSkinner
		caracteristicas[5] = (paletoSkinner / (w * h)) * 100;
//		calcaSkinner
		caracteristicas[6] = (calcaSkinner / (w * h)) * 100;
//		cabeloSkinner
		caracteristicas[7] = (cabeloSkinner / (w * h)) * 100;
//		gravataSkinner
		caracteristicas[8] = (gravataSkinner / (w * h)) * 100;

        //APRENDIZADO SUPERVISIONADO - JÁ SABE QUAL A CLASSE NAS IMAGENS DE TREINAMENTO
        caracteristicas[9] = f.getName().charAt(0)=='s'?0:1;
		
		return caracteristicas;
	}
	
	//	NED VALIDATION
	// DONE
	public static boolean isCamisaNed(double r, double g, double b) {
		 if (b >= 160 && b <= 200 &&  g >= 140 && g <= 180 &&  r >= 230 && r <= 250) {
        	return true;
        }
		 return false;
	}

	// DONE
	public static boolean isMoletomNed(double r, double g, double b) {
		 if (b >= 50 && b <= 95 &&  g >= 80 && g <= 150 &&  r >= 50 && r <= 110) {
        	return true;
        }
		 return false;
	}

	// DONE
	public static boolean isCalcaNed(double r, double g, double b) {
		 if (b >= 50 && b <= 90 &&  g >= 45 && g <= 80 &&  r >= 40 && r <= 90) {
        	return true;
        }
		 return false;
	}

	// DONE
	public static boolean isCabeloNed(double r, double g, double b) {
		 if (b >= 50 && b <= 100 &&  g >= 60 && g <= 140 &&  r >= 80 && r <= 160) {
        	return true;
        }
		 return false;
	}

	// -------------------------------------------------------------

	//	SKINNER VALIDATION
	
	public static boolean isCamisaSkinner(double r, double g, double b) {
		 if (b >= 170 && b <= 180 &&  g >= 110 && g <= 120 &&  r >= 150 && r <= 158) {
       	return true;
       }
		 return false;
	}
	
	public static boolean isPaletoSkinner(double r, double g, double b) {
		 if (b >= 150 && b <= 180 &&  g >= 60 && g <= 120 &&  r >= 50 && r <= 56) {
       	return true;
       }
		 return false;
	}
	
	public static boolean isCalcaSkinner(double r, double g, double b) {
		 if (b >= 15 && b <= 180 &&  g >= 70 && g <= 120 &&  r >= 50 && r <= 56) {
       	return true;
       }
		 return false;
	}
	
	public static boolean isCabeloSkinner(double r, double g, double b) {
		 if (b >= 70 && b <= 100 &&  g >= 80 && g <= 100 &&  r >= 100 && r <= 100) {
       	return true;
       }
		 return false;
	}
	
	public static boolean isGravataSkinner(double r, double g, double b) {
		 if (b >= 70 && b <= 90 &&  g >= 110 && g <= 120 &&  r >= 170 && r <= 200) {
       	return true;
       }
		 return false;
	}

	public static void extrair() {
				
	    // Cabeçalho do arquivo Weka
		String exportacao = "@relation caracteristicas\n\n";
		exportacao += "@attribute camisa_ned real\n";
		exportacao += "@attribute moletom_ned real\n";
		exportacao += "@attribute calca_ned real\n";
		exportacao += "@attribute cabelo_ned real\n";
		exportacao += "@attribute camisa_skinner real\n";
		exportacao += "@attribute paleto_skinner real\n";
		exportacao += "@attribute calca_skinner real\n";
		exportacao += "@attribute cabelo_skinner real\n";
		exportacao += "@attribute gravata_skinner real\n";
		exportacao += "@attribute classe {Ned, Skinner}\n\n";
		exportacao += "@data\n";
	        
	    // Diretório onde estão armazenadas as imagens
	    File diretorio = new File("src\\imgs");
	    File[] arquivos = diretorio.listFiles();
	    
        // Definição do vetor de características
        double[][] caracteristicas = new double[317][7];
        
        // Percorre todas as imagens do diretório
        int cont = -1;
        for (File imagem : arquivos) {
        	cont++;
        	caracteristicas[cont] = extraiCaracteristicas(imagem);
        	
        	String classe = caracteristicas[cont][9] == 0 ?"Skinner":"Ned";
        	
        	System.out.println("Camisa Ned: " + caracteristicas[cont][0]
            		+ "\t Moletom Ned: " + caracteristicas[cont][1]
            		+ "\t Calça Ned: " + caracteristicas[cont][2]
            		+ "\t Cabelo Ned: " + caracteristicas[cont][3]
            		+ "\t Camisa Skinner: " + caracteristicas[cont][4]
            		+ "\t Paleto Skinner: " + caracteristicas[cont][5]
    				+ "\t Calça Skinner: " + caracteristicas[cont][6]
					+ "\t Cabelo Skinner: " + caracteristicas[cont][7]
					+ "\t Gravata Skinner: " + caracteristicas[cont][8]
            		+ "\t Classe: " + classe);
        	
        	exportacao += caracteristicas[cont][0] + ","
                    + caracteristicas[cont][1] + ","
        		    + caracteristicas[cont][2] + ","
                    + caracteristicas[cont][3] + ","
        		    + caracteristicas[cont][4] + ","
                    + caracteristicas[cont][5] + ","
            		+ caracteristicas[cont][6] + ","
    				+ caracteristicas[cont][7] + ","
    				+ caracteristicas[cont][8] + ","
                    + classe + "\n";
        }
        
     // Grava o arquivo ARFF no disco
        try {
        	File arquivo = new File("caracteristicas.arff");
        	FileOutputStream f = new FileOutputStream(arquivo);
        	f.write(exportacao.getBytes());
        	f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

}
