import React, { Component } from 'react';
import classes from "./SirioSelect.module.css";

/**
 * Komponen select dropdown untuk Sirio Form
 * 
 * Props yang tersedia:
 * - name               : String, nama state yang akan dikaitkan
 * - value              : String, nilai dari state yang akan dikaitkan
 * - handleChange       : Function, fungsi untuk mengaitkan value dengan state
 * - options            : List of JSON, berisi value dan name dari tiap option
 */
export default class SirioSelect extends Component {
    render() {
        return (
            <select
                value={this.props.value}
                onChange={this.props.handleChange}
                name={this.props.name}
                className={classes.select}
            >
                {this.props.optionList.map((option, i) =>
                    <option key={i} value={option.value}>{option.name}</option>
                )}
            </select>
        );
    }
}