/*
 * Copyright 2009-2011 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.examples.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;

import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class DynamicImageController {

	private StreamedContent graphicText;
	
	private StreamedContent barcode;
	
	private StreamedContent chart;

	public DynamicImageController() {
        try {
            //Graphic Text
            BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bufferedImg.createGraphics();
            g2.drawString("This is a text", 0, 10);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImg, "png", os);
            graphicText = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png"); 

            //Chart
            JFreeChart jfreechart = ChartFactory.createPieChart("Turkish Cities", createDataset(), true, true, false);
            File chartFile = new File("dynamichart");
            ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 375, 300);
            chart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");

            //Barcode
			File barcodeFile = new File("dynamicbarcode");
			BarcodeImageHandler.saveJPEG(BarcodeFactory.createCode128("PRIMEFACES"), barcodeFile);
			barcode = new DefaultStreamedContent(new FileInputStream(barcodeFile), "image/jpeg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public StreamedContent getBarcode() {
		return barcode;
	}

    public StreamedContent getGraphicText() {
        return graphicText;
    }
		
	public StreamedContent getChart() {
		return chart;
	}
	
	private PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Istanbul", new Double(45.0));
		dataset.setValue("Ankara", new Double(15.0));
		dataset.setValue("Izmir", new Double(25.2));
		dataset.setValue("Antalya", new Double(14.8));

		return dataset;
	}
}
