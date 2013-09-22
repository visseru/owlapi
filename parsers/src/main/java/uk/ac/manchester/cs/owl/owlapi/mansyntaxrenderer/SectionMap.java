/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
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
package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;

/** Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22/12/2010 */
public class SectionMap {
    private Map<Object, Set<OWLAxiom>> object2Axioms = new HashMap<Object, Set<OWLAxiom>>();

    public boolean isEmpty() {
        return object2Axioms.isEmpty();
    }

    public void add(@Nonnull Object o, @Nonnull OWLAxiom forAxiom) {
        checkNotNull(o, "o cannot be null");
        checkNotNull(forAxiom, "forAxiom cannot be null");
        Set<OWLAxiom> axioms = object2Axioms.get(o);
        if (axioms == null) {
            axioms = new HashSet<OWLAxiom>();
            object2Axioms.put(o, axioms);
        }
        axioms.add(forAxiom);
    }

    public void remove(@Nonnull Object o) {
        object2Axioms.remove(checkNotNull(o, "o cannot be null"));
    }

    @Nonnull
    public Collection<Object> getSectionObjects() {
        return object2Axioms.keySet();
    }

    @Nonnull
    public Set<Set<OWLAnnotation>> getAnnotationsForSectionObject(
            @Nonnull Object sectionObject) {
        checkNotNull(sectionObject, "sectionObject cannot be null");
        Collection<OWLAxiom> axioms = object2Axioms.get(sectionObject);
        if (axioms == null) {
            return new HashSet<Set<OWLAnnotation>>();
        }
        Set<Set<OWLAnnotation>> annos = new HashSet<Set<OWLAnnotation>>();
        for (OWLAxiom ax : axioms) {
            annos.add(ax.getAnnotations());
        }
        return annos;
    }
}