import React, { Component } from 'react';
import SirioField from './SirioFormComponent/SirioField';
import SirioButton from "../../Components/Button/SirioButton";
import classes from "./SirioForm.module.css";

/**
 * Komponen form untuk Sirio secara umum
 * 
 * Props yang tersedia:
 * - title              : String, Judul Form
 * - headerButton       : Komponen, yang akan ditampilkan di sisi sebelah kanan title
 * - inputDefinition    : List of JSON, definisi dari tiap field. See also SirioField.
 * - onSubmit           : Function, fungsi yang akan dijalankan ketika user submit form. 
 * - submitButton       : Komponen, button submit. Jika tidak disediakan, akan menggunakan tombol default
 */
class SirioForm extends Component {

    render() {
        return (
            <>
                <div className={classes.headerWrapper}>
                    <h1 className={classes.title}>
                        {this.props.title}
                    </h1>
                    {this.props.headerButton}
                </div>
                <form className={classes.formWrapper} onSubmit={this.props.onSubmit}>
                    {this.props.inputDefinition.map((field, i) =>
                        <SirioField
                            key={i}
                            label={field.label}
                            handleChange={field.handleChange}
                            type={field.type}
                            name={field.name}
                            value={field.value}
                            placeholder={field.placeholder}
                        />
                    )}
                    {this.props.submitButton ? this.props.submitButton :
                        <div className="w-100 text-right">
                            <SirioButton
                                purple
                            >
                                Submit
                            </SirioButton>
                        </div>
                    }
                </form>
            </>
        );
    }
}

export default SirioForm;
