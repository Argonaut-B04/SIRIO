import React, { Component } from 'react';
import classes from "./SirioSelect.module.css";
import Select from "react-select";

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

    customStyles = {
        control: base => ({
            ...base,
            border: "none",
            margin: 0,
            boxShadow: 'none'   
        }),
        valueContainer: base => ({
            ...base,
            padding: 0
        }),
        option: (base, state) => ({
            ...base,
            padding: "0.5rem",
            fontFamily: "Nunito",
            color: state.isFocused ? "black" : "rgba(127, 63, 152, 0.9)",
            backgroundColor: "white",
            borderRadius: "20px"
        })
    }

    render() {
        return (
            <Select
                value={this.props.options.filter(option => option.value === this.props.value)}
                onChange={event => this.props.handleChange(this.props.name, event)}
                components={{ IndicatorSeparator:() => null }}
                options={this.props.options}
                className={classes.select}
                styles={this.customStyles}
            />
        );
    }
}