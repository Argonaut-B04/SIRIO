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
            boxShadow: 'none',
            height: "40px"
        }),
        valueContainer: base => ({
            ...base,
            padding: "5px",
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
        var hiddenOne;
        if (this.props.required) {
            hiddenOne =
                <input
                    tabIndex={-1}
                    autoComplete="off"
                    style={{ opacity: 0, height: 0, marginLeft: "-200px", marginTop: "10px" }}
                    value={this.props.options && this.props.options.filter(option => option.value === this.props.value)}
                    required
                />
        }
        return (
            <>
                <Select
                    value={this.props.options && this.props.options.filter(option => option.value === this.props.value)}
                    onChange={event => this.props.handleChange(this.props.name, event, this.props.index)}
                    components={{ IndicatorSeparator: () => null }}
                    options={this.props.options}
                    className={this.props.className ? [this.props.className, classes.select].join(" ") : classes.select}
                    styles={this.customStyles}
                />
                {hiddenOne}
            </>
        );
    }
}