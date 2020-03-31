import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelHasilPemeriksaan.module.css';
import SirioTable from '../SirioTable';
import HasilPemeriksaanService from '../../../Services/HasilPemeriksaanService';
import { NavLink } from 'react-router-dom';

export default class TabelHasilPemeriksaan extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: []
        }

        this.renderRows = this.renderRows.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    async renderRows() {
        const response = await HasilPemeriksaanService.getHasilPemeriksaanByLoggedInUser();

        this.setState({
            rowList: response.data.result
        })
    }

    statusFormatter(cell) {
        switch (cell) {
            case "Draft":
                return (
                    <span style={{ color: '#7F3F98' }}>{cell}</span>
                );
            case "Menunggu Persetujuan":
                return (
                    <span style={{ color: '#8E8E8E' }}>{cell}</span>
                );
            case "Ditolak":
                return (
                    <span style={{ color: '#FF0000' }}>{cell}</span>
                );
            case "Menunggu Pelaksanaan":
                return (
                    <span style={{ color: '#5DBCD2' }}>{cell}</span>
                );
            default:
                return (cell);
        }
    }

    getButtonsFirst(cell, row) {
        const siapDijalankan = row.siapDijalankan;
        const status = row.namaStatus;
        const jalankan = (siapDijalankan === 1 && status === "Menunggu Pelaksanaan");
        return (
            <SirioButton
                purple
                disabled={!jalankan}
            >
                Jalankan
            </SirioButton>
        )
    }

    getButtonsSecond(cell, row) {
        return (
            <NavLink to={{
                pathname: "/hasil-pemeriksaan/detail",
                state: {
                    id: row.id,
                }
            }}>
                <SirioButton
                    purple
                >
                    Detail Hasil
                </SirioButton>
            </NavLink>
        )
    }

    namaHasilPemeriksaanFormatter(cell, row) {
        return 'Kantor Cabang ' + row.tugasPemeriksaan.namaKantorCabang +
            ', Tugas Pemeriksaan #' + row.tugasPemeriksaan.id;
    }


    columns = [
        {
            dataField: '',
            isDummyField: true,
            text: 'HASIL PEMERIKSAAN',
            sort: true,
            classes: classes.rowItem,
            formatter: this.namaHasilPemeriksaanFormatter,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "25%", textAlign: 'left' };
            }
        }, {
            dataField: 'statusHasilPemeriksaan',
            text: 'STATUS',
            sort: true,
            classes: classes.rowItem,
            formatter: this.statusFormatter,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "20%", textAlign: 'left' };
            }
        }, {
            dataField: 'noData 1',
            text: '',
            headerClasses: classes.colheader,
            classes: classes.rowItem,
            formatter: this.getButtonsFirst,
            style: () => {
                return { textAlign: 'center' }
            }
        }, {
            dataField: 'noData 2',
            text: '',
            headerClasses: classes.colheader,
            classes: classes.rowItem,
            formatter: this.getButtonsSecond,
            style: () => {
                return { textAlign: 'center' }
            }
        }];

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    render() {
        return (
            <SirioTable
                title="Daftar Hasil Pemeriksaan"
                data={this.state.rowList}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
            />
        );
    }
}