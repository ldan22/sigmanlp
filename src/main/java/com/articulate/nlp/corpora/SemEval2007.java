package com.articulate.nlp.corpora;

import com.articulate.sigma.KBmanager;
import com.articulate.sigma.WordNet;
import com.articulate.sigma.WordNetUtilities;

import java.io.IOException;
import java.util.HashMap;

/**
 Copyright 2017 Articulate Software

 Author: Adam Pease apease@articulatesoftware.com

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program ; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 MA  02111-1307 USA

 */
public class SemEval2007 {

    // a map index by WordNet sense keys where values are counts of co-occurring words
    public static HashMap<String, HashMap<String,Integer>> senses = new HashMap<>();

    /***************************************************************
     */
    public static void load() {

        WordNet.wn.readSenseIndex(System.getProperty("user.home") + "/corpora/WordNet-2.0/dict/index.sense");
        try {
            WordNetUtilities.updateWNversionReading(System.getProperty("user.home") + "/corpora/mappings-upc-2007/mapping-30-21/", "30-21");
        }
        catch (IOException ioe) {
            System.out.println("Error in XtendedWN.main()" + ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    /***************************************************************
     */
    public static void main(String[] args) {

        KBmanager.getMgr().initializeOnce();
        load();
        System.out.println(senses);
    }
}
