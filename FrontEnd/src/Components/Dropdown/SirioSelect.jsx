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
            paddingLeft: "0.5rem",
            fontFamily: "Nunito",
            color: state.isFocused ? "white" : "rgba(127, 63, 152, 0.9)",
            backgroundColor: state.isFocused ? "rgba(127, 63, 152, 0.9)" : "white",
        }),
    }

    render() {
        const { customStyles, props } = this;
        const { options, required, value, name, index, handleChange, className } = props;
        const { select } = classes;
        var hiddenOne;
        if (required) {
            hiddenOne =
                <input
                    tabIndex={-1}
                    autoComplete="off"
                    readOnly
                    style={{ opacity: 0, height: 0, top: "40px", position: "absolute" }}
                    value={options && options.filter(option => option.value === value)}
                    required
                />
        }
        return (
            <>
                {hiddenOne}
                <Select
                    value={options && options.filter(option => option.value === value)}
                    onChange={event => handleChange(name, event, index)}
                    components={{ IndicatorSeparator: () => null }}
                    options={options}
                    className={[select, className].join(" ")}
                    styles={customStyles}
                    placeholder={placeholder}
                />
            </>
        );
    }
}