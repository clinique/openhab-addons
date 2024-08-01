/**
 * Copyright (c) 2010-2024 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.vigicrues.internal.dto.vigieau;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Answer} hold root information of the API answer
 *
 * @author Gaël L'hopital - Initial contribution
 */
public class Answer {
    public int id;
    public String code;
    @SerializedName("nom")
    public String name;
    public WaterType type;
    @SerializedName("niveauGravite")
    public Gravity gravity;
    public String departement;
    public Arrete arrete;
    @SerializedName("usages")
    public List<Allowance> allowances;
}
