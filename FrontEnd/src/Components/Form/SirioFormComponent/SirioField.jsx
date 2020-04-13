import React, { Component } from 'react';
import classes from "./SirioField.module.css";
import SirioSelect from '../../Dropdown/SirioSelect';
import TextareaAutosize from 'react-textarea-autosize';

/**
 * Komponen field untuk SirioForm
 * 
 * Props yang tersedia:
 * - label              : String, judul field
 * - customInput        : Komponen, jika ingin menggunakan selain input tag
 * - type               : String, tipe input tag
 * - name               : String, nama state yang akan dikaitkan dengan field ini
 * - value              : String, nilai dari name
 * - onChange           : Function, fungsi yang akan dijalankan ketika field diubah
 * - placeholder        : String, placeholder dari input tag
 */
export default class SirioField extends Component {

    render() {
        if (this.props.fullComponent) {
            return this.props.fullComponent;
        } else {
            const label =
                this.props.label ?
                    <label className={classes.label}>
                        {this.props.label}
                        {this.props.validator && <p className={classes.error}>{this.props.validator}</p>}
                    </label>
                    :
                    ""
                ;
            var field;
            if (this.props.customInput) {
                field = this.props.customInput;
            } else if (this.props.type === "select") {
                field =
                    <SirioSelect
                        index={this.props.index}
                        name={this.props.name}
                        value={this.props.value}
                        handleChange={this.props.handleChange}
                        options={this.props.optionList}
                        className={[this.props.classes, classes.sirioSelect].join(" ")}
                    />;
            } else if (this.props.type === "textarea") {
                field =
                    <TextareaAutosize
                        name={this.props.name}
                        value={this.props.value}
                        onChange={(event) => this.props.handleChange(event, this.props.index)}

                        placeholder={this.props.placeholder}
                        className={this.props.classes ? [this.props.calsses, classes.input].join(" ") : classes.input}
                        minRows={3}
                        maxRows={6}

                        required={this.props.required}
                    />
            } else if (this.props.type === "checkbox") {
                field =
                    <input
                        type={this.props.type}

                        name={this.props.name}
                        defaultChecked={this.props.value}
                        value={this.defaultChecked ? true : false}
                        onChange={this.props.handleChange}
                        index={this.props.index}

                        className={this.props.classes ? [this.props.classes, classes.input].join(" ") : classes.input}
                    />
            } else {
                field =
                    <input
                        type={this.props.type}

                        name={this.props.name}
                        value={this.props.value}
                        onChange={(event) => this.props.handleChange(event, this.props.index)}

                        min={this.props.min}

                        placeholder={this.props.placeholder}
                        className={this.props.classes ? [this.props.classes, classes.input].join(" ") : classes.input}

                        required={this.props.required}
                    />
            }

            return (
                <fieldset>
                    {label}
                    {field}
                </fieldset>
            )
        }
    }
}