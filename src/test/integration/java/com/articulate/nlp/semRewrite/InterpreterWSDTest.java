/*
Copyright 2014-2015 IPsoft

Author: Andrei Holub andrei.holub@ipsoft.com

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
package com.articulate.nlp.semRewrite;

import com.articulate.nlp.IntegrationTestBase;
import com.articulate.sigma.KBmanager;
import com.articulate.nlp.pipeline.Pipeline;
import com.articulate.nlp.pipeline.SentenceUtil;
import com.articulate.nlp.semRewrite.substitutor.NounSubstitutor;
import com.articulate.nlp.semRewrite.substitutor.SubstitutionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class InterpreterWSDTest extends IntegrationTestBase {

    public static Interpreter interp = new Interpreter();

    /** ***************************************************************
     */
    @BeforeClass
    public static void initClauses() {

        KBmanager.getMgr().initializeOnce();
        try {
            interp.initialize();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** ***************************************************************
     */
    @Test
    public void findWSD_NoGroups() {

        String input = "Amelia Mary Earhart was an American aviator.";
        Annotation wholeDocument = interp.userInputs.annotateDocument(input);
        CoreMap lastSentence = SentenceUtil.getLastSentence(wholeDocument);
        List<String> wsds = interp.findWSD(lastSentence);
        String[] expected = {
                //"names(Amelia-1,\"Amelia\")", // missed without real EntityParser information
                //"names(Mary-2,\"Mary\")",
                "sumo(DiseaseOrSyndrome,Amelia-1)", // from WordNet: Amelia
                "sumo(Woman,Mary-2)",
                "sumo(Woman,Earhart-3)",
                "sumo(UnitedStates,American-6)",
                "sumo(Pilot,aviator-7)"
        };
        assertThat(wsds, hasItems(expected));
        assertEquals(expected.length, wsds.size());
    }

    /** ***************************************************************
     */
    @Test
    public void findWSD_WithGroups() {

        String input = "Amelia Mary Earhart (July 24, 1897 - July 2, 1937) was an American aviator.";
        Annotation wholeDocument = interp.userInputs.annotateDocument(input);
        CoreMap lastSentence = SentenceUtil.getLastSentence(wholeDocument);
        List<String> wsds = interp.findWSD(lastSentence);
        String[] expected = {
                //"names(AmeliaMaryEarhart-1,\"Amelia Mary Earhart\")", // missed without real EntityParser information
                "sumo(UnitedStates,American-17)",
                "sumo(Pilot,aviator-18)"
        };
        assertThat(wsds, hasItems(expected));
        assertEquals(expected.length, wsds.size());
    }
}