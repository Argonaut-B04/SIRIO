import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioTable from '../SirioTable';
import RekomendasiService from '../../../Services/RekomendasiService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import classes from '../Rekomendasi/TabelRekomendasi.module.css';
import SirioMessageButton from '../../Button/ActionButton/SirioMessageButton';

class TabelBuktiPelaksanaan extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            openNotification: true
        }

        this.renderRows = this.renderRows.bind(this);
        this.endNotification = this.endNotification.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    endNotification() {
        this.setState({
            openNotification: false
        })
    }

    async renderRows() {
        const response = await RekomendasiService.getRekomendasiByLoggedInUser();

        this.setState({
            rowList: response.data.result
        })
    }

    statusFormatter(cell) {
        switch (cell) {
            case "Menunggu Persetujuan":
                return (
                    <span style={{ color: '#8E8E8E' }}>{cell}</span>
                );
            case "Disetujui":
                return (
                    <span style={{ color: '#5DBCD2' }}>{cell}</span>
                );
            case "Ditolak":
                return (
                    <span style={{ color: '#FF0000' }}>{cell}</span>
                );
            default:
                return (cell);
        }
    }

    // getButtonFirst(cell, row) {
    //     const status = row.statusBukti;
    //     const adaBukti = status === "Menunggu Persetujuan" || status === "Disetujui" || status === "Ditolak";

    //     if (adaBukti) {
    //         return (
    //             <NavLink to={{
    //                 pathname: "/bukti-pelaksanaan/detail",
    //                 state: {
    //                     id: row.id
    //                 }
    //             }}>
    //                 <SirioButton
    //                     purple
    //                 >
    //                     Detail Bukti
    //                 </SirioButton>
    //             </NavLink>
    //         )
    //     }
    // }

    getButtons(cell, row) {
        const status = row.statusBukti;
        const tanpaBukti = status === null;
        const adaBukti = status === "Menunggu Persetujuan" || status === "Disetujui" || status === "Ditolak";

        if (tanpaBukti) {
            return (
                <NavLink to={{
                    pathname: "/bukti-pelaksanaan/tambah",
                    state: {
                        id: row.id
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Tambah Bukti
                    </SirioButton>
                </NavLink>
            )
        } else if (adaBukti) {
            return (
                <NavLink to={{
                    pathname: "/bukti-pelaksanaan/detail",
                    state: {
                        id: row.id
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Detail Bukti
                    </SirioButton>
                </NavLink>
            )
        }
    }

    // getColumns() {
    //     // const columns = [];
    //     const role = this.state.role;
    //     const firstColumn = role === "admin" || role === "Manajer Operational Risk";
    //     const secondColumn = role === "Branch Manager";

    //     console.log(secondColumn)
    //     if (firstColumn) {
    //         return (
    //             [{
    //                 dataField: 'keterangan',
    //                 text: 'KETERANGAN',
    //                 // sort: true,
    //                 classes: classes.rowItem,
    //                 headerClasses: classes.colheader,
    //                 headerStyle: (colum, colIndex) => {
    //                     return { width: "20%", textAlign: 'left' };
    //                 }
    //             }, {
    //                 dataField: 'namaKantorCabang',
    //                 text: 'KANTOR CABANG',
    //                 // sort: true,
    //                 classes: classes.rowItem,
    //                 headerClasses: classes.colheader,
    //                 headerStyle: (colum, colIndex) => {
    //                     return { width: "15%", textAlign: 'left' };
    //                 }
    //             }, {
    //                 dataField: 'tenggatWaktu',
    //                 text: 'TENGGAT WAKTU',
    //                 // sort: true,
    //                 classes: classes.rowItem,
    //                 headerClasses: classes.colheader,
    //                 headerStyle: (colum, colIndex) => {
    //                     return { width: "15%", textAlign: 'left' };
    //                 }
                    
    //             }, {
    //                 dataField: 'durasi',
    //                 text: 'DURASI',
    //                 // sort: true,
    //                 classes: classes.rowItem,
    //                 headerClasses: classes.colheader,
    //                 headerStyle: (colum, colIndex) => {
    //                     return { width: "10%", textAlign: 'left' };
    //                 }
                    
    //             }, {
    //                 dataField: 'statusBukti',
    //                 text: 'STATUS BUKTI',
    //                 // sort: true,
    //                 classes: classes.rowItem,
    //                 formatter: this.statusFormatter,
    //                 headerClasses: classes.colheader,
    //                 headerStyle: (colum, colIndex) => {
    //                     return { width: "20%", textAlign: 'left' };
    //                 }
    //             }, {
    //                 dataField: 'action',
    //                 text: '',
    //                 headerClasses: classes.colheader,
    //                 classes: classes.rowItem,
    //                 style: () => {
    //                     return { textAlign: 'center' }
    //                 },
    //                 formatter: this.getButtons
    //             }]
    //         );
    //     } else if (secondColumn) {
    //         return (
    //             [{
    //                 dataField: 'keterangan',
    //                 text: 'KETERANGAN',
    //                 // sort: true,
    //                 classes: classes.rowItem,
    //                 headerClasses: classes.colheader,
    //                 headerStyle: (colum, colIndex) => {
    //                     return { width: "25%", textAlign: 'left' };
    //                 }
            
    //             }, {
    //                 dataField: 'tenggatWaktu',
    //                 text: 'TENGGAT WAKTU',
    //                 // sort: true,
    //                 classes: classes.rowItem,
    //                 headerClasses: classes.colheader,
    //                 headerStyle: (colum, colIndex) => {
    //                     return { width: "20%", textAlign: 'left' };
    //                 }
                    
    //             }, {
    //                 dataField: 'durasi',
    //                 text: 'DURASI',
    //                 // sort: true,
    //                 classes: classes.rowItem,
    //                 headerClasses: classes.colheader,
    //                 headerStyle: (colum, colIndex) => {
    //                     return { width: "12%", textAlign: 'left' };
    //                 }
                    
    //             }, {
    //                 dataField: 'statusBukti',
    //                 text: 'STATUS BUKTI',
    //                 // sort: true,
    //                 classes: classes.rowItem,
    //                 formatter: this.statusFormatter,
    //                 headerClasses: classes.colheader,
    //                 headerStyle: (colum, colIndex) => {
    //                     return { width: "20%", textAlign: 'left' };
    //                 }
    //             }, {
    //                 dataField: 'action',
    //                 text: '',
    //                 headerClasses: classes.colheader,
    //                 classes: classes.rowItem,
    //                 style: () => {
    //                     return { textAlign: 'center' }
    //                 },
    //                 formatter: this.getButtons
    //             }]
    //         );
    //     }
    // }

    columns = [{
        dataField: 'keterangan',
        text: 'KETERANGAN',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }, {
        dataField: 'namaKantorCabang',
        text: 'KANTOR CABANG',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' };
        }
    }, {
        dataField: 'tenggatWaktu',
        text: 'TENGGAT WAKTU',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' };
        }
        
    }, {
        dataField: 'durasi',
        text: 'DURASI',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "10%", textAlign: 'left' };
        }
        
    }, {
        dataField: 'statusBukti',
        text: 'STATUS BUKTI',
        sort: true,
        classes: classes.rowItem,
        formatter: this.statusFormatter,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }, {
        dataField: 'action',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: this.getButtons
    }]

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    render() {
        return (
            <>
                <SirioTable
                    title="Daftar Bukti Pelaksanaan Rekomendasi"
                    data={this.state.rowList}
                    id='id'
                    columnsDefinition={this.columns}
                    includeSearchBar
                />
                {this.props.location.state && this.props.location.state.approveSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Bukti pelaksanaan berhasil disetujui"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
                {this.props.location.state && this.props.location.state.rejectSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Bukti pelaksanaan berhasil ditolak"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
            </>
        );
    }
} 

export default withRouter(TabelBuktiPelaksanaan);