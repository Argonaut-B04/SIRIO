import React, { Component } from 'react';
import SirioField from './SirioFormComponent/SirioField';
import classes from "./SirioFormWithDetail.module.css";
import BootstrapTable from "react-bootstrap-table-next";
import ToolkitProvider from 'react-bootstrap-table2-toolkit';
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css"
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';

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
class SirioFormWithDetail extends Component {

    render() {
        return (
            <>
                <div className={classes.headerWrapper}>
                    <h2 className={classes.title}>
                        {this.props.title}
                    </h2>
                    {this.props.headerButton}
                </div>
                <div className={classes.toolkitWrapper}>
                    <ToolkitProvider
                        bootstrap4
                        keyField={this.props.id}
                        data={this.props.data}
                        columns={this.props.columnsDefinition}
                    >
                        {
                            props => (
                                <div>
                                    <BootstrapTable
                                        {...props.baseProps}
                                        bordered={ false }
                                    />
                                    
                                </div>
                            )
                        }
                        
                    </ToolkitProvider>
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
                                optionList={field.optionList}
                            />
                        )}
                        <div className="w-100 text-right">
                            <br></br>
                            {this.props.submitButton}
                        </div>
                    </form>
                </div>
                
            </>
        );
    }
}

export default SirioFormWithDetail;
