import React, { Component } from 'react';
import classes from "./SirioField.module.css";

class SirioField extends Component {

    // constructor(props) {
    //     super(props);

    // }

    render() {
        if (this.props.customInput) {
            return (
                <fieldset>
                    <label className={classes.label}>
                        {this.props.label}
                    </label>
                    {this.props.customInput}
                </fieldset>
            )
        }
        return (
            <fieldset>
                <label className={classes.label}>
                    {this.props.label}
                </label>
                <input
                    type={this.props.type}

                    name={this.props.name}
                    value={this.props.value}
                    onChange={this.props.handleChange}

                    placeholder={this.props.placeholder}
                    className={classes.input}
                />
            </fieldset>
        );
    }
}

export default SirioField;
