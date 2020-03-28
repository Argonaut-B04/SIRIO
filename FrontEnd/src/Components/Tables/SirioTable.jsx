import React, { Component } from 'react';
import BootstrapTable from "react-bootstrap-table-next";
import paginationFactory from 'react-bootstrap-table2-paginator';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import overlayFactory from 'react-bootstrap-table2-overlay';
import classes from './SirioTable.module.css';
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css"
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';

/**
 * Kelas komponen tabel untuk Sirio secara umum
 * 
 * Props yang tersedia  :
 * - title              : String, Judul tabel
 * - headerButton       : Komponen, Akan ditampilkan di sisi kanan title
 * - id                 : String, parameter json 'id' dari data
 * - data               : JSON, data yang akan ditampilkan berdasarkan definisi columns
 * - columns            : List of JSON, definisi mengenai apa yang harus ditampilkan pada kolom tersebut. Referensi: react-bootstra-table2 columns playground
 * - includeSearchBar   : Boolean, switch untuk menampilkan searchbar atau tidak
 */
export default class SirioTable extends Component {

    // Fungsi untuk menampilkan hasil return jika tidak ada data yang ditampilkan pada tabel
    indication() {
        return "No Data in Table"
    }

    // Fungsi untuk menampilkan informasi jumlah entry tabel
    // TODO: Ubah menjadi sesuai dengan desain
    customTotal = (from, to, size) => (
        <span className="react-bootstrap-table-pagination-total text-secondary ml-5">
            Show {from} to {to} of {size} entries
        </span>
    );

    // Fungsi untuk menampilkan nomor baris pada tabel
    // (BUGGED, NOT FIXED)
    // TODO: Perbaiki
    pageButtonRenderer = ({
        page,
        active,
        disabled,
        title,
        onPageChange
    }) => {
        const handleClick = (e) => {
            e.preventDefault();
            onPageChange(page);
        };
        return (
            <li className="page-item" key={page} >
                <a href="https://www.google.com/" className="sirio-pagination-button" onClick={handleClick}>{page}</a>
            </li>
        );
    };

    // Definisi kustomisasi Pagination
    pagination = paginationFactory({
        sizePerPage: 10,
        prePageText: "Previous",
        nextPageText: "Next",
        showTotal: true,
        paginationTotalRenderer: this.customTotal,
        // pageButtonRenderer: this.pageButtonRenderer
    })

    // Overlay adalah animasi singkat yang ditampilkan pada tabel ketika isi tabel sedang dirender
    overlay = overlayFactory({
        spinner: true,
        styles: {
            overlay: (base) => (
                {
                    ...base,
                    background: 'rgba(255, 0, 0, 0.5)'
                }
            )
        }
    })

    // Fungsi render SirioTable
    render() {
        const { SearchBar } = Search;
        return (
            <div>
                <div className={classes.headerWrapper}>
                    <h1 className={classes.title}>
                        {this.props.title}
                    </h1>
                    {this.props.headerButton}
                </div>
                <div className={classes.toolkitWrapper}>
                    <ToolkitProvider
                        bootstrap4
                        keyField={this.props.id}
                        data={this.props.data}
                        columns={this.props.columnsDefinition}
                        defaultSorted={this.props.defaultSorted}
                        search
                    >
                        {
                            props => (
                                <div>
                                    {this.props.includeSearchBar ?
                                        <div className="text-right">
                                            <label className="m-2">Search: </label>
                                            <SearchBar
                                                {...props.searchProps}
                                                placeholder=""
                                            />
                                        </div>
                                        :
                                        null
                                    }
                                    <BootstrapTable
                                        {...props.baseProps}
                                        ref={n => this.node = n}
                                        striped
                                        hover
                                        condensed
                                        noDataIndication={this.indication}
                                        pagination={this.pagination}
                                        overlay={this.overlay}
                                        classes="table-responsive-lg"
                                    />
                                </div>
                            )
                        }
                    </ToolkitProvider>
                </div>
            </div>
        );
    }
};