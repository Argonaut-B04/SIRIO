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
        } else if (this.props.customInput) {
            return (
                <fieldset>
                    {this.props.label ?
                        <label className={classes.label}>
                            {this.props.label}
                        </label>
                        :
                        ""
                    }
                    {this.props.customInput}
                </fieldset>
            )
        } else if (this.props.type === "select") {
            return (
                <fieldset>
                    {this.props.label ?
                        <label className={classes.label}>
                            {this.props.label}
                        </label>
                        :
                        ""
                    }
                    <SirioSelect
                        index={this.props.index}
                        name={this.props.name}
                        value={this.props.value}
                        handleChange={this.props.handleChange}
                        options={this.props.optionList}
                        className={[this.props.classes, classes.sirioSelect].join(" ")}
                    />
                </fieldset>
            )
        } else if (this.props.type === "textarea") {
            return (
                <fieldset>
                    {this.props.label ?
                        <label className={classes.label}>
                            {this.props.label}
                        </label>
                        :
                        ""
                    }
                    <TextareaAutosize
                        name={this.props.name}
                        value={this.props.value}
                        onChange={(event) => this.props.handleChange(event, this.props.index)}

                        placeholder={this.props.placeholder}
                        className={this.props.classes ? [this.props.calsses, classes.input].join(" ") : classes.input}
                        minRows={3}
                        maxRows={6}
                    />
                </fieldset>
            )
        } else if (this.props.type === "checkbox") {
            return (
                <fieldset>
                    {this.props.label ?
                        <label className={classes.label} type={this.props.type}>
                            {this.props.label}
                        </label>
                        :
                        ""
                    }
                    <input
                        type={this.props.type}

                        name={this.props.name}
                        defaultChecked={this.props.value}
                        value={this.defaultChecked ? true : false}
                        onChange={this.props.handleChange}
                        index={this.props.index}

                        className={this.props.classes ? [this.props.classes, classes.input].join(" ") : classes.input}
                    />
                    <span className="checkmark"></span>
                </fieldset>
            );
        }
        return (
            <fieldset>
                {this.props.label ?
                    <label className={classes.label} type={this.props.type}>
                        {this.props.label}
                    </label>
                    :
                    ""
                }
                <input
                    type={this.props.type}

                    name={this.props.name}
                    value={this.props.value}
                    onChange={(event) => this.props.handleChange(event, this.props.index)}

                    placeholder={this.props.placeholder}
                    className={this.props.classes ? [this.props.classes, classes.input].join(" ") : classes.input}
                />
            </fieldset>
        );
    }
}